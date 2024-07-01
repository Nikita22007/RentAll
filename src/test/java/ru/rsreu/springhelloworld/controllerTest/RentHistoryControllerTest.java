package ru.rsreu.springhelloworld.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.tinkoff.rentall.controller.RentHistoryController;
import ru.tinkoff.rentall.dto.RentHistoryDTO;
import ru.tinkoff.rentall.entity.Advertisement;
import ru.tinkoff.rentall.entity.Chat;
import ru.tinkoff.rentall.entity.RentHistory;
import ru.tinkoff.rentall.entity.User;
import ru.tinkoff.rentall.mapper.ChatMapper;
import ru.tinkoff.rentall.mapper.RentHistoryMapper;
import ru.tinkoff.rentall.repository.AdvertisementRepository;
import ru.tinkoff.rentall.repository.RentHistoryRepository;
import ru.tinkoff.rentall.repository.UserRepository;
import ru.tinkoff.rentall.service.RentHistoryService;


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

@Import({RentHistoryController.class, RentHistoryService.class})
@ExtendWith(SpringExtension.class)
public class RentHistoryControllerTest {


    @Autowired // сюда подставляется компонент
    private RentHistoryController rentHistoryController;

    @MockBean
    private RentHistoryRepository rentHistoryRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AdvertisementRepository advertisementRepository;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private RentHistory rentHistory = new RentHistory();

    private User user = new User();

    private Advertisement advertisement = new Advertisement();

    private EasyRandom esr;

    private EasyRandomParameters parameters;


    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(rentHistoryController).build();
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
        rentHistory = esr.nextObject(RentHistory.class);
        user = esr.nextObject(User.class);
        advertisement = esr.nextObject(Advertisement.class);
    }

    @Test
    void setAdvertisement_successful() throws Exception {
        RentHistoryDTO rentHistoryDTO = RentHistoryMapper.INSTANCE.toRentHistoryDTO(this.rentHistory);
        String rentHistoryJson = objectMapper.writeValueAsString(rentHistoryDTO);
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(ArgumentMatchers.any());
        Mockito.doReturn(Optional.of(advertisement)).when(advertisementRepository).findById(ArgumentMatchers.any());
        Mockito.doReturn(this.rentHistory).when(rentHistoryRepository).save(ArgumentMatchers.any());

        mockMvc.perform(post("/set_rent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(rentHistoryJson))
                .andExpect(status().isCreated());
        verify(userRepository, times(1)).findById(ArgumentMatchers.any());
        verify(advertisementRepository, times(1)).findById(ArgumentMatchers.any());
        verify(rentHistoryRepository, times(1)).save(ArgumentMatchers.any());
    }


    @Test
    void getRentHistoriesByUserLogin_successful() throws Exception {
        List<RentHistory> rentHistoryList = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            rentHistoryList.add(esr.nextObject(RentHistory.class));
            rentHistoryList.get(i).getUser().setLogin("User_1");
        }
        Mockito.doReturn(rentHistoryList).when(rentHistoryRepository).findByUser_Login(ArgumentMatchers.any());

        mockMvc.perform(get("/rent_history/user/{lesseeLogin}", "User_1"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[*].lesseeLogin",
                        containsInAnyOrder(rentHistoryList.get(0).getUser().getLogin(),
                                rentHistoryList.get(1).getUser().getLogin(),
                                rentHistoryList.get(2).getUser().getLogin())))
                .andExpect(jsonPath("$[*].advId",
                        containsInAnyOrder(rentHistoryList.get(0).getAdvertisement().getAdvId(),
                                rentHistoryList.get(1).getAdvertisement().getAdvId(),
                                rentHistoryList.get(2).getAdvertisement().getAdvId())));
        verify(rentHistoryRepository, times(1)).findByUser_Login(ArgumentMatchers.any());
    }


    @Test
    void getRentHistoriesByUserLogin_notSuccessful() throws Exception {
        List<RentHistory> rentHistoryList = new ArrayList<>();
        Mockito.doReturn(rentHistoryList).when(rentHistoryRepository).findByUser_Login(ArgumentMatchers.any());

        mockMvc.perform(get("/rent_history/user/{lesseeLogin}", "User_1"))
                .andExpect(status().is(404));
        verify(rentHistoryRepository, times(1)).findByUser_Login(ArgumentMatchers.any());
    }




}
