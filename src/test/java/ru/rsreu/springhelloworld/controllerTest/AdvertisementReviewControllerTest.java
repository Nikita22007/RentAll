package ru.rsreu.springhelloworld.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;
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
import ru.tinkoff.rentall.controller.AdvertisementReviewController;
import ru.tinkoff.rentall.dto.AdvertisementReviewDTO;
import ru.tinkoff.rentall.entity.AdvertisementReview;
import ru.tinkoff.rentall.mapper.AdvertisementReviewMapper;
import ru.tinkoff.rentall.repository.AdvertisementRepository;
import ru.tinkoff.rentall.repository.AdvertisementReviewRepository;
import ru.tinkoff.rentall.repository.UserRepository;
import ru.tinkoff.rentall.service.AdvertisementReviewService;


import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({AdvertisementReviewController.class, AdvertisementReviewService.class})
@ExtendWith(SpringExtension.class)
public class AdvertisementReviewControllerTest {


    @Autowired // сюда подставляется компонент
    private AdvertisementReviewController advertisementReviewController;

    @MockBean
    private AdvertisementReviewRepository advertisementReviewRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AdvertisementRepository advertisementRepository;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private AdvertisementReview advertisementReview = new AdvertisementReview();

    private EasyRandom esr;

    private EasyRandomParameters parameters;


    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(advertisementReviewController).build();
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
    }

    @Test
    void createAdvertisementReview_successful() throws Exception {
        this.advertisementReview = esr.nextObject(AdvertisementReview.class);
        AdvertisementReviewDTO advertisementReviewDTO = AdvertisementReviewMapper.INSTANCE.toAdvertisementReviewDTO(this.advertisementReview);
        String advertisementReviewJson = objectMapper.writeValueAsString(advertisementReviewDTO);
        Mockito.doReturn(Optional.of(this.advertisementReview.getUser())).when(userRepository).findById(ArgumentMatchers.any());
        Mockito.doReturn(Optional.of(this.advertisementReview.getAdvertisement())).when(advertisementRepository).findById(ArgumentMatchers.any());
        Mockito.doReturn(this.advertisementReview).when(advertisementReviewRepository).save(ArgumentMatchers.any());
        mockMvc.perform(post("/create_advertisement_review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(advertisementReviewJson))
                .andExpect(status().isCreated());
        verify(advertisementReviewRepository, times(1)).save(ArgumentMatchers.any());
        verify(userRepository, times(1)).findById(ArgumentMatchers.any());
        verify(advertisementRepository, times(1)).findById(ArgumentMatchers.any());
    }

    @Test
    void getAdvertisementReviews_successful() throws Exception {
        List<AdvertisementReview> advertisementReviews = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            advertisementReviews.add(this.esr.nextObject(AdvertisementReview.class));
        }
        Mockito.doReturn(advertisementReviews).when(advertisementReviewRepository).findAll();

        mockMvc.perform(get("/advertisement_review"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[*].userLogin",
                        containsInAnyOrder(advertisementReviews.get(0).getUser().getLogin(),
                                advertisementReviews.get(1).getUser().getLogin(),
                                advertisementReviews.get(2).getUser().getLogin())))
                .andExpect(jsonPath("$[*].postId",
                        containsInAnyOrder(advertisementReviews.get(0).getAdvertisement().getAdvId(),
                                advertisementReviews.get(1).getAdvertisement().getAdvId(),
                                advertisementReviews.get(2).getAdvertisement().getAdvId())))
                .andExpect(jsonPath("$[*].feedback",
                        containsInAnyOrder(advertisementReviews.get(0).getFeedback(),
                                advertisementReviews.get(1).getFeedback(),
                                advertisementReviews.get(2).getFeedback())))
                .andExpect(jsonPath("$[*].mark",
                        containsInAnyOrder(advertisementReviews.get(0).getMark(),
                                advertisementReviews.get(1).getMark(),
                                advertisementReviews.get(2).getMark())));
        verify(advertisementReviewRepository, times(1)).findAll();
    }

    @Test
    void getReviewsByAdvertisementId_successful() throws Exception {
        List<AdvertisementReview> advertisementReviews = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            advertisementReviews.add(this.esr.nextObject(AdvertisementReview.class));
            advertisementReviews.get(i).getAdvertisement().setAdvId(25);
        }
        Mockito.doReturn(advertisementReviews).when(advertisementReviewRepository).findByAdvertisement_AdvId(25);

        mockMvc.perform(get("/advertisement_review/advertisement/{advertisementId}", 25))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[*].userLogin",
                        containsInAnyOrder(advertisementReviews.get(0).getUser().getLogin(),
                                advertisementReviews.get(1).getUser().getLogin(),
                                advertisementReviews.get(2).getUser().getLogin())))
                .andExpect(jsonPath("$[*].postId",
                        containsInAnyOrder(advertisementReviews.get(0).getAdvertisement().getAdvId(),
                                advertisementReviews.get(1).getAdvertisement().getAdvId(),
                                advertisementReviews.get(2).getAdvertisement().getAdvId())))
                .andExpect(jsonPath("$[*].feedback",
                        containsInAnyOrder(advertisementReviews.get(0).getFeedback(),
                                advertisementReviews.get(1).getFeedback(),
                                advertisementReviews.get(2).getFeedback())))
                .andExpect(jsonPath("$[*].mark",
                        containsInAnyOrder(advertisementReviews.get(0).getMark(),
                                advertisementReviews.get(1).getMark(),
                                advertisementReviews.get(2).getMark())));
        verify(advertisementReviewRepository, times(1)).findByAdvertisement_AdvId(25);
    }

    @Test
    void getAverageMarkByAdvertisementId_successful() throws Exception {
        List<AdvertisementReview> advertisementReviews = new ArrayList<>();
        for (int i = 0; i < 6; i++){
            advertisementReviews.add(this.esr.nextObject(AdvertisementReview.class));
            advertisementReviews.get(i).getAdvertisement().setAdvId(25);
            advertisementReviews.get(i).setMark(5);
        }
        advertisementReviews.get(2).setMark(4);
        advertisementReviews.get(3).setMark(4);
        Mockito.doReturn(advertisementReviews).when(advertisementReviewRepository).findByAdvertisement_AdvId(25);

        mockMvc.perform(get("/advertisement_review/advertisement/{advertisementId}/average_mark", 25))
                .andExpect(status().is(200))
                        .andExpect(jsonPath("$").value(4.67));
        verify(advertisementReviewRepository, times(1)).findByAdvertisement_AdvId(25);
    }


    @Test
    void getReviewsByUserLogin_successful() throws Exception {
        List<AdvertisementReview> advertisementReviews = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            advertisementReviews.add(this.esr.nextObject(AdvertisementReview.class));
            advertisementReviews.get(i).getUser().setLogin("Greg");
        }
        Mockito.doReturn(advertisementReviews).when(advertisementReviewRepository).findByUser_Login("Greg");

        mockMvc.perform(get("/advertisement_review/user/{userLogin}", "Greg"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[*].userLogin",
                        containsInAnyOrder(advertisementReviews.get(0).getUser().getLogin(),
                                advertisementReviews.get(1).getUser().getLogin(),
                                advertisementReviews.get(2).getUser().getLogin())))
                .andExpect(jsonPath("$[*].postId",
                        containsInAnyOrder(advertisementReviews.get(0).getAdvertisement().getAdvId(),
                                advertisementReviews.get(1).getAdvertisement().getAdvId(),
                                advertisementReviews.get(2).getAdvertisement().getAdvId())))
                .andExpect(jsonPath("$[*].feedback",
                        containsInAnyOrder(advertisementReviews.get(0).getFeedback(),
                                advertisementReviews.get(1).getFeedback(),
                                advertisementReviews.get(2).getFeedback())))
                .andExpect(jsonPath("$[*].mark",
                        containsInAnyOrder(advertisementReviews.get(0).getMark(),
                                advertisementReviews.get(1).getMark(),
                                advertisementReviews.get(2).getMark())));
        verify(advertisementReviewRepository, times(1)).findByUser_Login("Greg");
    }


}
