package ru.rsreu.springhelloworld.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.tinkoff.rentall.controller.ImageController;
import ru.tinkoff.rentall.dto.ImageDTO;
import ru.tinkoff.rentall.entity.Image;
import ru.tinkoff.rentall.mapper.ImageMapper;
import ru.tinkoff.rentall.repository.ImageRepository;
import ru.tinkoff.rentall.service.ImageService;


import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({ImageController.class, ImageService.class})
@ExtendWith(SpringExtension.class)
public class ImageControllerTest {


    @Autowired // сюда подставляется компонент
    private ImageController imageController;

    @MockBean
    private ImageRepository imageRepository;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private ImageDTO imageDTO = new ImageDTO();

    private EasyRandom esr;

    private EasyRandomParameters parameters;


    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(df);
        this.parameters = new EasyRandomParameters()
                .charset(StandardCharsets.UTF_8)
                .stringLengthRange(5, 20)
                .collectionSizeRange(1, 10);
        this.esr = new EasyRandom(this.parameters);
    }

    @Test
    void sendMessageTest_successful() throws Exception {
        InputStream inputStream = ImageControllerTest.class.getResourceAsStream("/Images/test.jpg");
        MockMultipartFile testImage = new MockMultipartFile("file", "test.jpg", "image/jpg", inputStream);
        imageDTO.setImgName(testImage.getBytes());
        Image image = ImageMapper.INSTANCE.toImage(imageDTO);
        Mockito.doReturn(image).when(imageRepository).save(ArgumentMatchers.any());
        mockMvc.perform(multipart("/image_upload").file(testImage))
                .andExpect(status().isOk()).andExpect(jsonPath("$.imgId").value(image.getImgId()));
        verify(imageRepository, times(1)).save(ArgumentMatchers.any());
    }

    @Test
    void sendMessageTest_notSuccessful() throws Exception {
        InputStream inputStream = ImageControllerTest.class.getResourceAsStream("/Images/test.jpg");
        mockMvc.perform(multipart("/image_upload", inputStream))
                .andExpect(status().is(400));
        verify(imageRepository, times(0)).save(ArgumentMatchers.any());
    }

    @Test
    void imageLoad_successful() throws Exception {
        InputStream inputStream = ImageControllerTest.class.getResourceAsStream("/Images/test.jpg");
        MockMultipartFile testImage = new MockMultipartFile("file", "test.jpg", "image/jpg", inputStream);
        imageDTO.setImgName(testImage.getBytes());
        Image image = ImageMapper.INSTANCE.toImage(imageDTO);
        Mockito.doReturn(Optional.of(image)).when(imageRepository).findById(ArgumentMatchers.any());
        mockMvc.perform(get("/image_load/{id}", 0)).andDo(print())
                .andExpect(status().isOk());
        verify(imageRepository, times(1)).findById(ArgumentMatchers.any());
    }

    @Test
    void imageLoad_notSuccessful() throws Exception {
        Mockito.doReturn(Optional.empty()).when(imageRepository).findById(ArgumentMatchers.any());
        mockMvc.perform(get("/image_load/{id}", 0))
                .andExpect(status().is(400));
        verify(imageRepository, times(1)).findById(ArgumentMatchers.any());
    }

}
