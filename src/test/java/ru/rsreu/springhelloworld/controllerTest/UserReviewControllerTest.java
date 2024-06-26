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
import ru.tinkoff.rentall.controller.UserReviewController;
import ru.tinkoff.rentall.dto.MessageDTO;
import ru.tinkoff.rentall.dto.UserReviewDTO;
import ru.tinkoff.rentall.entity.Message;
import ru.tinkoff.rentall.entity.User;
import ru.tinkoff.rentall.entity.UserReview;
import ru.tinkoff.rentall.mapper.UserReviewMapper;
import ru.tinkoff.rentall.repository.UserRepository;
import ru.tinkoff.rentall.repository.UserReviewRepository;
import ru.tinkoff.rentall.service.UserReviewService;


import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

@Import({UserReviewController.class, UserReviewService.class})
@ExtendWith(SpringExtension.class)
public class UserReviewControllerTest {


    @Autowired // сюда подставляется компонент
    private UserReviewController userReviewController;

    @MockBean
    private UserReviewRepository userReviewRepository;

    @MockBean
    private UserRepository userRepository;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private UserReview userReview = new UserReview();

    private EasyRandom esr;
    private EasyRandomParameters parameters;


    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(userReviewController).build();
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
        this.userReview = esr.nextObject(UserReview.class);

    }

    @Test
    void getUserReviewByTargetLogin_successful() throws Exception {
        List<UserReview> userReviews = new ArrayList<>();
        List<UserReviewDTO> userReviewDTOSs = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            userReviews.add(this.esr.nextObject(UserReview.class));
            userReviews.get(i).getTargetLogin().setUserFullName("TargetLogin");
            userReviewDTOSs.add( UserReviewMapper.INSTANCE.toUserReviewDTO(userReviews.get(i)));
        }
        Mockito.doReturn(userReviews).when(userReviewRepository).findByTargetLogin_Login(ArgumentMatchers.any());

        mockMvc.perform(get("/user_review/{targetLogin}", "UserLogin"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].targetLogin").value(userReviewDTOSs.get(0).getTargetLogin()))
                .andExpect(jsonPath("$[1].targetLogin").value(userReviewDTOSs.get(1).getTargetLogin()))
                .andExpect(jsonPath("$[2].targetLogin").value(userReviewDTOSs.get(2).getTargetLogin()))
        ;
        verify(userReviewRepository, times(1)).findByTargetLogin_Login(ArgumentMatchers.any());
    }

    @Test
    void getUserReviewByTargetLogin_notSuccessful() throws Exception {
        Mockito.doReturn(new ArrayList<>()).when(userReviewRepository).findByTargetLogin_Login(ArgumentMatchers.any());

        mockMvc.perform(get("/user_review/{targetLogin}", "UserLogin"))
                .andExpect(status().is(404));
        ;
        verify(userReviewRepository, times(1)).findByTargetLogin_Login(ArgumentMatchers.any());
    }

    @Test
    void getUserReviews_successful() throws Exception {
        List<UserReview> userReviews = new ArrayList<>();
        List<UserReviewDTO> userReviewDTOSs = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            userReviews.add(this.esr.nextObject(UserReview.class));
            userReviewDTOSs.add( UserReviewMapper.INSTANCE.toUserReviewDTO(userReviews.get(i)));
        }
        Mockito.doReturn(userReviews).when(userReviewRepository).findAll();

        mockMvc.perform(get("/user_review"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[*].targetLogin", containsInAnyOrder(userReviewDTOSs.get(0).getTargetLogin(),
                        userReviewDTOSs.get(1).getTargetLogin(), userReviewDTOSs.get(2).getTargetLogin())));
        verify(userReviewRepository, times(1)).findAll();
    }

    @Test
    void getUserReviewByUserLogin_successful() throws Exception {
        List<UserReview> userReviews = new ArrayList<>();
        List<UserReviewDTO> userReviewDTOSs = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            userReviews.add(this.esr.nextObject(UserReview.class));
            userReviews.get(i).getUserLogin().setUserFullName("UserLogin");
            userReviewDTOSs.add( UserReviewMapper.INSTANCE.toUserReviewDTO(userReviews.get(i)));
        }
        Mockito.doReturn(userReviews).when(userReviewRepository).findByUserLogin_Login(ArgumentMatchers.any());

        mockMvc.perform(get("/user_review/user/{userLogin}", "UserLogin"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].targetLogin").value(userReviewDTOSs.get(0).getTargetLogin()))
                .andExpect(jsonPath("$[1].targetLogin").value(userReviewDTOSs.get(1).getTargetLogin()))
                .andExpect(jsonPath("$[2].targetLogin").value(userReviewDTOSs.get(2).getTargetLogin()))
        ;
        verify(userReviewRepository, times(1)).findByUserLogin_Login(ArgumentMatchers.any());
    }

    @Test
    void getUserReviewByUserLogin_notSuccessful() throws Exception {
        Mockito.doReturn(new ArrayList<>()).when(userReviewRepository).findByUserLogin_Login(ArgumentMatchers.any());

        mockMvc.perform(get("/user_review/user/{userLogin}", "UserLogin"))
                .andExpect(status().is(404));
        ;
        verify(userReviewRepository, times(1)).findByUserLogin_Login(ArgumentMatchers.any());
    }

    @Test
    void createUserReview_Successful() throws Exception {
        UserReviewDTO userReviewDTO = UserReviewMapper.INSTANCE.toUserReviewDTO(this.userReview);
        Mockito.doReturn(Optional.of(this.userReview.getUserLogin())).when(userRepository).findById(userReviewDTO.getUserLogin());
        Mockito.doReturn(Optional.of(this.userReview.getTargetLogin())).when(userRepository).findById(userReviewDTO.getTargetLogin());
        Mockito.doReturn(this.userReview).when(userReviewRepository).save(ArgumentMatchers.any());
        String userReviewDTOJson = objectMapper.writeValueAsString(userReviewDTO);
        mockMvc.perform(post("/create_user_review").contentType(MediaType.APPLICATION_JSON)
                        .content(userReviewDTOJson))
                .andExpect(status().isCreated());
        ;
        verify(userReviewRepository, times(1)).save(ArgumentMatchers.any());
        verify(userRepository, times(2)).findById(ArgumentMatchers.any());
    }

    @Test
    void getAverageMarkByTargetLogin_Successful() throws Exception {
        UserReviewDTO userReviewDTO = UserReviewMapper.INSTANCE.toUserReviewDTO(this.userReview);
        Mockito.doReturn(Optional.of(this.userReview.getUserLogin())).when(userRepository).findById(userReviewDTO.getUserLogin());
        Mockito.doReturn(Optional.of(this.userReview.getTargetLogin())).when(userRepository).findById(userReviewDTO.getTargetLogin());
        Mockito.doReturn(this.userReview).when(userReviewRepository).save(ArgumentMatchers.any());
        String userReviewDTOJson = objectMapper.writeValueAsString(userReviewDTO);
        mockMvc.perform(post("/create_user_review").contentType(MediaType.APPLICATION_JSON)
                        .content(userReviewDTOJson))
                .andExpect(status().isCreated());
        ;
        verify(userReviewRepository, times(1)).save(ArgumentMatchers.any());
        verify(userRepository, times(2)).findById(ArgumentMatchers.any());
    }

}
