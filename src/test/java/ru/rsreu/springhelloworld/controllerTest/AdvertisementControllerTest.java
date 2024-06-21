package ru.rsreu.springhelloworld.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.rsreu.springhelloworld.testEntities.AdvertisementTestEntity;
import ru.rsreu.springhelloworld.testEntities.UserTestEntity;
import ru.tinkoff.rentall.controller.AdvertisementController;
import ru.tinkoff.rentall.dto.AdvertisementDTO;
import ru.tinkoff.rentall.entity.Advertisement;
import ru.tinkoff.rentall.mapper.AdvertisementMapper;
import ru.tinkoff.rentall.repository.AdvertisementRepository;
import ru.tinkoff.rentall.repository.UserRepository;
import ru.tinkoff.rentall.service.AdvertisementService;

import java.util.Optional;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@Import({AdvertisementController.class, AdvertisementService.class})
@ExtendWith(SpringExtension.class)
public class AdvertisementControllerTest {


    @Autowired // сюда подставляется компонент
    private AdvertisementController advController;

    @MockBean
    private AdvertisementRepository advertisementRepository;

    @MockBean
    private UserRepository userRepository;


    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private Advertisement adv;



    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(advController).build();
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        objectMapper = new ObjectMapper();
//        objectMapper.setDateFormat(df);
        this.adv = AdvertisementTestEntity.getAdvertisement();

    }

    @Test
    void getAdvertisement() throws Exception {
        AdvertisementDTO advDTO = AdvertisementMapper.INSTANCE.toAdvertisementDTO(adv);
        System.out.print(objectMapper.writeValueAsString(advDTO));
        Mockito.doReturn(Optional.of(adv)).when(advertisementRepository).findById(0L);
        mockMvc.perform(get("/advertisement/{adv_id}", 0L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.advName").value(adv.getAdvName()))
                .andExpect(jsonPath("$.description").value(adv.getDescription()))
                .andExpect(jsonPath("$.timeUnit").value(adv.getTimeUnit()))
                .andExpect(jsonPath("$.rentTime").value(adv.getRentTime()))
                .andExpect(jsonPath("$.barterAllowed").value(adv.isBarterAllowed()))
                .andExpect(jsonPath("$.userLogin").value(adv.getUser().getLogin()))
                .andExpect(jsonPath("$.imageId").value(adv.getImageId()))
                .andExpect(jsonPath("$.categoryId").value(adv.getCategoryId()))
                .andExpect(jsonPath("$.creationTime").value(objectMapper.writeValueAsString(adv.getCreationTime())));
        verify(advertisementRepository, times(1)).findById(0L);
    }

    @Test
    void setAdvertisement_successful() throws Exception {
        AdvertisementDTO advDTO = AdvertisementMapper.INSTANCE.toAdvertisementDTO(this.adv);
        String advJson = objectMapper.writeValueAsString(advDTO);
        doReturn(adv).when(advertisementRepository).save(ArgumentMatchers.any());
        doReturn(Optional.of(UserTestEntity.getUser())).when(userRepository).findById(ArgumentMatchers.any());
        mockMvc.perform(post("/create_advertisement")
                .contentType(MediaType.APPLICATION_JSON)
                .content(advJson))
                .andExpect(status().isCreated());
        verify(userRepository, times(1)).findById(ArgumentMatchers.any());
        verify(advertisementRepository, times(1)).save(ArgumentMatchers.any());
    }



}
