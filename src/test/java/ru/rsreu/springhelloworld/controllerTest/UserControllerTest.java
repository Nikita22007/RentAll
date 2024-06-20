package ru.rsreu.springhelloworld.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.rsreu.springhelloworld.testEntities.UserTestEntity;
import ru.tinkoff.rentall.controller.UserController;
import ru.tinkoff.rentall.dto.LoginDTO;
import ru.tinkoff.rentall.dto.UserDTO;
import ru.tinkoff.rentall.entity.User;
import ru.tinkoff.rentall.mapper.UserMapper;
import ru.tinkoff.rentall.repository.UserRepository;

import ru.tinkoff.rentall.service.UserService;

import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({UserController.class, UserService.class})
@ExtendWith(SpringExtension.class)
public class UserControllerTest {


    @Autowired // сюда подставляется компонент
    private UserController userController;

    @MockBean
    private UserRepository userRepository;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private UserDTO userDTO = new UserDTO();
    private User user = new User();


    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(df);
        this.user = UserTestEntity.getUser();
        this.userDTO = UserMapper.INSTANCE.toUserDTO(user);
    }

    @Test
    void setUser() throws Exception {
        String userJson = objectMapper.writeValueAsString(userDTO);
        Mockito.doReturn(new User()).when(userRepository).save(ArgumentMatchers.any());

        mockMvc.perform(post("/registrate_user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated());
        verify(userRepository, times(1)).save(ArgumentMatchers.any());
    }

    @Test
    void loginUser_correctPasswd() throws Exception {
        // инициализация логина
        LoginDTO login = new LoginDTO();
        login.setUserPassword(userDTO.getUserPassword());
        login.setLogin(userDTO.getLogin());
        String loginJson = objectMapper.writeValueAsString(login);
        // вызов тестируемого метода
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(ArgumentMatchers.any());
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.userFullName").value(user.getUserFullName()));
        verify(userRepository, times(1)).findById(ArgumentMatchers.any());
    }

    @Test
    void loginUser_wrongPasswd() throws Exception {
        // инициализация логина
        LoginDTO login = new LoginDTO();
        login.setUserPassword(userDTO.getUserPassword());
        login.setLogin(userDTO.getLogin());
        // инициализация неправильного пароля пользователя
        user.setUserPassword("wrongPasswd");
        // создание входных данных для вызова метода
        String loginJson = objectMapper.writeValueAsString(login);
        // вызов тестируемого метода
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(ArgumentMatchers.any());
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().is(400));
        verify(userRepository, times(1)).findById(ArgumentMatchers.any());
    }

    @Test
    void loginUser_tooBigUserName() throws Exception {
        String expectedName = user.getUserFullName();
        user.setUserFullName("*".repeat(60));
        String userJson = objectMapper.writeValueAsString(userDTO);
        Mockito.doReturn(new User()).when(userRepository).save(ArgumentMatchers.any());
        mockMvc.perform(post("/registrate_user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().is(400));

        verify(userRepository, times(1)).save(ArgumentMatchers.any());
    }


}
