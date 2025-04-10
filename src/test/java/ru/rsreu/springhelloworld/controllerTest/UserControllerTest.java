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
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.tinkoff.rentall.controller.UserController;
import ru.tinkoff.rentall.dto.LoginDTO;
import ru.tinkoff.rentall.dto.UserDTO;
import ru.tinkoff.rentall.entity.User;

import ru.tinkoff.rentall.mapper.UserMapper;
import ru.tinkoff.rentall.repository.UserRepository;

import ru.tinkoff.rentall.security.JWTUtil;
import ru.tinkoff.rentall.security.UserValidator;
import ru.tinkoff.rentall.service.AuthService;
import ru.tinkoff.rentall.service.UserService;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import({UserController.class,
        UserService.class,
        UserValidator.class,
        AuthService.class,
        JWTUtil.class,
        AuthenticationManager.class,
        UserRepository.class,
        PasswordEncoder.class})
@ExtendWith(SpringExtension.class)
public class UserControllerTest {


    @Autowired // сюда подставляется компонент
    private UserController userController;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    AuthenticationManager authenticationManager;


    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private EasyRandomParameters parameters;

    private UserDTO userDTO = new UserDTO();
    private User user = new User();


    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(df);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        this.parameters = new EasyRandomParameters()
                .charset(StandardCharsets.UTF_8)
                .stringLengthRange(5, 20)
                .collectionSizeRange(1, 10);
        EasyRandom esr = new EasyRandom(this.parameters);
        this.user = esr.nextObject(User.class);
        this.userDTO = UserMapper.INSTANCE.toUserDTO(user);


    }

    @Test
    void setUser_successful() throws Exception {
        String userJson = objectMapper.writeValueAsString(userDTO);
        Mockito.doReturn(this.user).when(userRepository).save(ArgumentMatchers.any());
        Mockito.doReturn(Optional.empty()).when(userRepository).findById(ArgumentMatchers.any());
        Mockito.doReturn(user).when(userRepository).save(ArgumentMatchers.any());
        mockMvc.perform(post("/registrate_user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.login").value(userDTO.getLogin()))
                .andExpect(jsonPath("$.userFullName").value(userDTO.getUserFullName()));
        verify(userRepository, times(1)).save(ArgumentMatchers.any());
        verify(userRepository, times(2)).findById(ArgumentMatchers.any());
        verify(passwordEncoder, times(1)).encode(ArgumentMatchers.any());
    }

    @Test
    void loginUser_correctPasswd() throws Exception {
        // инициализация логина
        LoginDTO login = new LoginDTO();
        login.setUserPassword(userDTO.getUserPassword());
        login.setLogin(userDTO.getLogin());
        String loginJson = objectMapper.writeValueAsString(login);
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(ArgumentMatchers.any());
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.userFullName").value(user.getUserFullName()));
        verify(userRepository, times(1)).findById(ArgumentMatchers.any());
        verify(authenticationManager, times(1)).authenticate(ArgumentMatchers.any());
        verify(userRepository, times(1)).findById(ArgumentMatchers.any());

    }

    @Test
    void loginUser_wrongPasswd() throws Exception {
        LoginDTO login = new LoginDTO();
        login.setUserPassword(userDTO.getUserPassword());
        login.setLogin(userDTO.getLogin());
        user.setUserPassword("wrongPasswd");
        String loginJson = objectMapper.writeValueAsString(login);
        Mockito.doThrow(BadCredentialsException.class).when(authenticationManager).authenticate(ArgumentMatchers.any());

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().is4xxClientError());
        verify(userRepository, times(0)).findById(ArgumentMatchers.any());
        verify(authenticationManager, times(1)).authenticate(ArgumentMatchers.any());
        verify(userRepository, times(0)).findById(ArgumentMatchers.any());
    }
}
