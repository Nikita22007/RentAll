package ru.rsreu.springhelloworld.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.tinkoff.rentall.controller.DocumentController;
import ru.tinkoff.rentall.dto.DocumentDTO;
import ru.tinkoff.rentall.entity.Advertisement;
import ru.tinkoff.rentall.entity.RentHistory;
import ru.tinkoff.rentall.entity.User;
import ru.tinkoff.rentall.repository.AdvertisementRepository;
import ru.tinkoff.rentall.repository.RentHistoryRepository;
import ru.tinkoff.rentall.repository.UserRepository;
import ru.tinkoff.rentall.service.DocumentService;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Optional;
import static java.nio.charset.Charset.forName;
import static org.hamcrest.Matchers.containsInAnyOrder;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import({DocumentController.class, DocumentService.class})
@ExtendWith(SpringExtension.class)
public class DocumentControllerTest {


    @Autowired // сюда подставляется компонент
    private DocumentController documentController;

    @MockBean
    private RentHistoryRepository rentHistoryRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AdvertisementRepository advertisementRepository;


    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private RentHistory rentHistory = new RentHistory();

    private DocumentDTO documentDTO = new DocumentDTO();

    private EasyRandomParameters parameters;

    private EasyRandom esr;


    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(documentController).build();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(df);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        this.parameters = new EasyRandomParameters()
                .charset(StandardCharsets.UTF_8)
                .stringLengthRange(5, 20)
                .collectionSizeRange(1, 10);
        this.esr = new EasyRandom(this.parameters);
        this.rentHistory = esr.nextObject(RentHistory.class);
        this.documentDTO = esr.nextObject(DocumentDTO.class);
    }

    @Test
    void generateDocuments_successful() throws Exception {
        Advertisement advertisement = rentHistory.getAdvertisement();
        User lesseeUser = rentHistory.getUser();
        User lessorUser = advertisement.getUser();

        String outputName = "src/main/resources/documents/" + lessorUser.getUserFullName()
                + "_" + lesseeUser.getUserFullName() + "_" + advertisement.getAdvName() + ".docx";

        Mockito.doReturn(Optional.of(rentHistory)).when(rentHistoryRepository).findById(this.documentDTO.getRentId());
        String documentJson = objectMapper.writeValueAsString(this.documentDTO);

        mockMvc.perform(post("/generate-documents")
                        .contentType(MediaType.APPLICATION_JSON).content(documentJson))
                .andExpect(status().isOk());

        Assertions.assertTrue(new File(outputName).exists());
        Assertions.assertTrue(new File(outputName).delete());
    }

    @Test
    void generateDocuments_notSuccessful() throws Exception {
        Advertisement advertisement = rentHistory.getAdvertisement();
        User lesseeUser = rentHistory.getUser();
        User lessorUser = advertisement.getUser();

        String outputName = "src/main/resources/documents/" + lessorUser.getUserFullName()
                + "_" + lesseeUser.getUserFullName() + "_" + advertisement.getAdvName() + ".docx";

        Mockito.doReturn(Optional.empty()).when(rentHistoryRepository).findById(this.documentDTO.getRentId());
        String documentJson = objectMapper.writeValueAsString(this.documentDTO);

        mockMvc.perform(post("/generate-documents")
                        .contentType(MediaType.APPLICATION_JSON).content(documentJson))
                .andExpect(status().is4xxClientError());
        Assertions.assertFalse(new File(outputName).exists());
        Assertions.assertFalse(new File(outputName).delete());
    }




}
