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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.rsreu.springhelloworld.testEntities.AdvertisementTestEntity;
import ru.rsreu.springhelloworld.testEntities.UserTestEntity;
import ru.tinkoff.rentall.controller.AdvertisementController;
import ru.tinkoff.rentall.dto.AdvertisementDTO;
import ru.tinkoff.rentall.entity.Advertisement;
import ru.tinkoff.rentall.entity.User;
import ru.tinkoff.rentall.mapper.AdvertisementMapper;
import ru.tinkoff.rentall.repository.AdvertisementRepository;
import ru.tinkoff.rentall.repository.UserRepository;
import ru.tinkoff.rentall.service.AdvertisementService;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static java.nio.charset.Charset.forName;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hibernate.engine.internal.Versioning.seed;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    private EasyRandomParameters parameters;


    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(advController).build();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(df);
        this.adv = AdvertisementTestEntity.getAdvertisement();
        this.parameters = new EasyRandomParameters()
                .charset(StandardCharsets.UTF_8)
                .stringLengthRange(5, 20)
                .collectionSizeRange(1, 10);

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
                .andExpect(jsonPath("$.creationTime").value(adv.getCreationTime().getTime()));
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
                .andExpect(status().isCreated()).andReturn();
        verify(userRepository, times(1)).findById(ArgumentMatchers.any());
        verify(advertisementRepository, times(1)).save(ArgumentMatchers.any());
    }


    @Test
    void getAdvertisementBoard_successful() throws Exception {
        List<AdvertisementDTO> dtoList = new ArrayList<>();
        List<Advertisement> advList = new ArrayList<>();
        EasyRandom esr = new EasyRandom(this.parameters);
        for (int i = 0; i < 4; i++){
            //dtoList.add(new EasyRandom(this.parameters).nextObject(AdvertisementDTO.class));
            User tempUser = esr.nextObject(User.class);
            Advertisement tempAdv = esr.nextObject(Advertisement.class);
            tempAdv.setUser(tempUser);
            advList.add(tempAdv);
            dtoList.add(AdvertisementMapper.INSTANCE.toAdvertisementDTO(tempAdv));
        }
        doReturn(advList).when(advertisementRepository).findAll();
        mockMvc.perform(get("/advertisement_board"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].advName", containsInAnyOrder(dtoList.get(0).getAdvName(),
                        dtoList.get(1).getAdvName(), dtoList.get(2).getAdvName(), dtoList.get(3).getAdvName())))
                .andExpect(jsonPath("$[*].description", containsInAnyOrder(dtoList.get(0).getDescription(),
                        dtoList.get(1).getDescription(), dtoList.get(2).getDescription(), dtoList.get(3).getDescription())))
                .andExpect(jsonPath("$[*].timeUnit", containsInAnyOrder(dtoList.get(0).getTimeUnit(),
                        dtoList.get(1).getTimeUnit(), dtoList.get(2).getTimeUnit(), dtoList.get(3).getTimeUnit())))
                .andExpect(jsonPath("$[*].barterAllowed", containsInAnyOrder(dtoList.get(0).isBarterAllowed(),
                        dtoList.get(1).isBarterAllowed(), dtoList.get(2).isBarterAllowed(), dtoList.get(3).isBarterAllowed())))
                .andExpect(jsonPath("$[*].userLogin", containsInAnyOrder(dtoList.get(0).getUserLogin(),
                        dtoList.get(1).getUserLogin(), dtoList.get(2).getUserLogin(), dtoList.get(3).getUserLogin())))
                .andExpect(jsonPath("$[*].imageId", containsInAnyOrder(dtoList.get(0).getImageId(),
                        dtoList.get(1).getImageId(), dtoList.get(2).getImageId(), dtoList.get(3).getImageId())))
                .andExpect(jsonPath("$[*].categoryId", containsInAnyOrder(dtoList.get(0).getCategoryId(),
                        dtoList.get(1).getCategoryId(), dtoList.get(2).getCategoryId(), dtoList.get(3).getCategoryId())))
                .andExpect(jsonPath("$[*].creationTime", containsInAnyOrder(dtoList.get(0).getCreationTime().getTime(),
                        dtoList.get(1).getCreationTime().getTime(), dtoList.get(2).getCreationTime().getTime(),
                        dtoList.get(3).getCreationTime().getTime())));


    }

    @Test
    void deleteAdvertisement_successful() throws Exception {
        doReturn(Optional.of(AdvertisementTestEntity.getAdvertisement())).when(advertisementRepository).findById(ArgumentMatchers.any());
        mockMvc.perform(delete("/delete_advertisement/{adv_id}", 0L))
                .andExpect(status().isOk());
        verify(advertisementRepository, times(1)).findById(ArgumentMatchers.any());
        verify(advertisementRepository, times(1)).delete(ArgumentMatchers.any());
    }

    @Test
    void deleteAdvertisement_notFoundUser() throws Exception {
        doReturn(Optional.empty()).when(advertisementRepository).findById(ArgumentMatchers.any());
        mockMvc.perform(delete("/delete_advertisement/{adv_id}", 0L))
                .andExpect(status().is(400));
        verify(advertisementRepository, times(1)).findById(ArgumentMatchers.any());
        verify(advertisementRepository, times(0)).delete(ArgumentMatchers.any());
    }







}
