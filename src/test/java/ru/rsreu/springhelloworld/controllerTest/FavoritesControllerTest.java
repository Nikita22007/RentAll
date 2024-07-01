package ru.rsreu.springhelloworld.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
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
import ru.rsreu.springhelloworld.testEntities.AdvertisementTestEntity;
import ru.rsreu.springhelloworld.testEntities.UserTestEntity;
import ru.tinkoff.rentall.controller.FavoritesController;
import ru.tinkoff.rentall.dto.AdBoardDTO;
import ru.tinkoff.rentall.dto.AdvertisementDTO;
import ru.tinkoff.rentall.dto.FavoritesDTO;
import ru.tinkoff.rentall.dto.SearchDTO;
import ru.tinkoff.rentall.entity.Advertisement;
import ru.tinkoff.rentall.entity.Favorites;
import ru.tinkoff.rentall.entity.RentHistory;
import ru.tinkoff.rentall.entity.User;
import ru.tinkoff.rentall.mapper.AdBoardMapper;
import ru.tinkoff.rentall.mapper.AdvertisementMapper;
import ru.tinkoff.rentall.mapper.FavoritesMapper;
import ru.tinkoff.rentall.repository.FavoritesRepository;
import ru.tinkoff.rentall.service.FavoritesService;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static java.nio.charset.Charset.forName;
import static org.hamcrest.Matchers.containsInAnyOrder;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import({FavoritesController.class, FavoritesService.class})
@ExtendWith(SpringExtension.class)
public class FavoritesControllerTest {


    @Autowired // сюда подставляется компонент
    private FavoritesController favoritesController;

    @MockBean
    private FavoritesRepository favoritesRepository;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private Favorites favorites;

    private EasyRandomParameters parameters;

    private EasyRandom esr;


    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(favoritesController).build();
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
        this.favorites = esr.nextObject(Favorites.class);
    }

    @Test
    void mark_successful() throws Exception {
        FavoritesDTO favoritesDTO = FavoritesMapper.INSTANCE.toFavoritesDTO(favorites);
        String favoritesDTOJson = objectMapper.writeValueAsString(favoritesDTO);
        Mockito.doReturn(favorites).when(favoritesRepository).save(ArgumentMatchers.any());
        mockMvc.perform(post("/mark_favorite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(favoritesDTOJson))
                .andExpect(status().isCreated());

        verify(favoritesRepository, times(1)).save(ArgumentMatchers.any());
    }


    @Test
    void getFavorites_successful() throws Exception {
        List<Favorites> favoritesList = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            favoritesList.add(esr.nextObject(Favorites.class));
            favoritesList.get(i).setUserLogin("User_1");
        }
        Mockito.doReturn(favoritesList).when(favoritesRepository).findByUserLogin("User_1");
        mockMvc.perform(get("/favorites/{login}", "User_1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].advId",
                        containsInAnyOrder(favoritesList.get(0).getAdvId(),
                                favoritesList.get(1).getAdvId(),
                                favoritesList.get(2).getAdvId())))
                .andExpect(jsonPath("$[*].userLogin",
                        containsInAnyOrder(favoritesList.get(0).getUserLogin(),
                                favoritesList.get(1).getUserLogin(),
                                favoritesList.get(2).getUserLogin())));

        verify(favoritesRepository, times(1)).findByUserLogin(ArgumentMatchers.any());
    }

}
