package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class TelegramBotUpdatesListenerTest {

    @Mock
    private TelegramBot telegramBot;
    @InjectMocks
    private TelegramBotUpdatesListener service;

    @Mock
    SendResponse response;


    @Test
    public void sendMessageTest() {
        //given
        Long chatId = 123L;
        String message = "test_message";

        SendMessage sendMessage = new SendMessage(chatId,message);

        //when
        service.sendMessage(chatId, message);

        //then
        Mockito.verify(telegramBot).execute(sendMessage);
    }

}