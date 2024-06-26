package ru.rsreu.springhelloworld.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.tinkoff.rentall.controller.ChatController;
import ru.tinkoff.rentall.dto.ChatDTO;
import ru.tinkoff.rentall.entity.Chat;
import ru.tinkoff.rentall.mapper.ChatMapper;
import ru.tinkoff.rentall.repository.ChatRepository;
import ru.tinkoff.rentall.service.ChatService;


import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({ChatController.class, ChatService.class})
@ExtendWith(SpringExtension.class)
public class ChatControllerTest {


    @Autowired // сюда подставляется компонент
    private ChatController chatController;

    @MockBean
    private ChatRepository chatRepository;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private ChatDTO chatDTO = new ChatDTO();

    private EasyRandom esr;

    private EasyRandomParameters parameters;


    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(chatController).build();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(df);
        this.parameters = new EasyRandomParameters()
                .charset(StandardCharsets.UTF_8)
                .stringLengthRange(5, 20)
                .collectionSizeRange(1, 10);
        this.esr = new EasyRandom(this.parameters);
    }

    @Test
    void sendMessageTest_successful() throws Exception {
        chatDTO = esr.nextObject(ChatDTO.class);
        Chat chat = ChatMapper.INSTANCE.toChat(chatDTO);
        String chatJson = objectMapper.writeValueAsString(chatDTO);
        Mockito.doReturn(chat).when(chatRepository).save(ArgumentMatchers.any());
        Mockito.doReturn(Optional.empty()).when(chatRepository).findById(ArgumentMatchers.any());

        mockMvc.perform(post("/create_chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(chatJson))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.chatId").value(chatDTO.getChatId()))
                .andExpect(jsonPath("$.userOneLogin").value(chatDTO.getUserOneLogin()))
                .andExpect(jsonPath("$.userTwoLogin").value(chatDTO.getUserTwoLogin()))
                .andExpect(jsonPath("$.chatName").value(chatDTO.getChatName()));
        verify(chatRepository, times(1)).save(ArgumentMatchers.any());
        verify(chatRepository, times(1)).findById(ArgumentMatchers.any());
    }

    @Test
    void sendMessageTest_messageAlreadyExists() throws Exception {
        chatDTO = esr.nextObject(ChatDTO.class);
        Chat chat = ChatMapper.INSTANCE.toChat(chatDTO);
        String chatJson = objectMapper.writeValueAsString(chatDTO);
        System.out.print(chatJson);
        Mockito.doReturn(chat).when(chatRepository).save(ArgumentMatchers.any());
        Mockito.doReturn(Optional.of(chat)).when(chatRepository).findById(ArgumentMatchers.any());
        mockMvc.perform(post("/create_chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(chatJson))
                .andExpect(status().is(400));
        verify(chatRepository, times(0)).save(ArgumentMatchers.any());
        verify(chatRepository, times(1)).findById(ArgumentMatchers.any());
    }

}
