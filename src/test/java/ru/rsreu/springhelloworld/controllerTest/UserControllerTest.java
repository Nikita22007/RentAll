package ru.rsreu.springhelloworld.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.rsreu.rentall.controller.UserController;
import ru.rsreu.rentall.dto.LoginDTO;
import ru.rsreu.rentall.dto.UserDTO;
import ru.rsreu.rentall.entity.User;
import ru.rsreu.rentall.mapper.UserMapper;
import ru.rsreu.rentall.repository.UserRepository;

import ru.rsreu.rentall.service.UserService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        userDTO.setUserAddress("string");
        userDTO.setUserPassword("12345");
        userDTO.setUserFullName("string");
        userDTO.setLogin("string");
        userDTO.setPhoneNumber("string");
        userDTO.setCreationTime(new Timestamp(System.currentTimeMillis()));


    }

    @Test
    void setUser() throws Exception {
        System.out.println(objectMapper.writeValueAsString(userDTO));
        String userJson = objectMapper.writeValueAsString(userDTO);
        Mockito.doReturn(new User()).when(userRepository).save(ArgumentMatchers.any());

        mockMvc.perform(post("/registrate_user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated());
        verify(userRepository, times(1)).save(ArgumentMatchers.any());
    }

    @Test
    void loginUser() throws Exception {
        LoginDTO login = new LoginDTO();
        login.setUserPassword(userDTO.getUserPassword());
        login.setLogin(userDTO.getLogin());
        String loginJson = objectMapper.writeValueAsString(login);
        user = UserMapper.INSTANCE.toUser(userDTO);
        User user = new User();
        user.setUserPassword(this.userDTO.getUserPassword());
        user.setUserFullName(this.userDTO.getUserFullName());
        user.setUserAddress(this.userDTO.getUserAddress());
        user.setPhoneNumber(this.userDTO.getPhoneNumber());
        user.setCreationTime(this.userDTO.getCreationTime());
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(ArgumentMatchers.any());
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().is(200));
        verify(userRepository, times(1)).findById(ArgumentMatchers.any());
    }


}
