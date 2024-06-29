package ru.rsreu.springhelloworld.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.tinkoff.rentall.controller.MessageController;
import ru.tinkoff.rentall.dto.MessageDTO;
import ru.tinkoff.rentall.entity.Message;
import ru.tinkoff.rentall.repository.MessageRepository;
import ru.tinkoff.rentall.service.MessageService;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import({MessageController.class, MessageService.class})
@ExtendWith(SpringExtension.class)
@Component
public class MessageControllerTest {


    @Autowired // сюда подставляется компонент
    private MessageController msgController;

    @MockBean
    private MessageRepository msgRepository;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private MessageDTO msgDTO = new MessageDTO();


    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(msgController).build();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(df);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void sendMessageTest() throws Exception {
        msgDTO.setChatId(1);
        msgDTO.setMsg("Привет! Как дела?");
        msgDTO.setSendingTime(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        String userJson = objectMapper.writeValueAsString(msgDTO);
        Mockito.doReturn(new Message()).when(msgRepository).save(ArgumentMatchers.any());

        mockMvc.perform(post("/chat/message/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated());
        verify(msgRepository, times(1)).save(ArgumentMatchers.any());
    }

}
