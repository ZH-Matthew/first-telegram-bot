package pro.sky.telegrambot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.repository.NotificationRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TelegramBotApplicationTests {

	@Mock
	private NotificationRepository repository;

	@Mock
	private Update update;

	@Mock
	private Message message;
	@Mock
	private TelegramBot bot;
	@Mock
	private Chat chat;

	@Mock
	List<Update> updates;

	@Mock
	TelegramBotUpdatesListener service;

	@Test
	void processStartTest() {

		String answer = "О! Привет!";
		Long chatId = 123L;
		SendMessage messageTest = new SendMessage(chatId, answer);

		when(updates.get(anyInt())).thenReturn(update); 	// при любом вызове update из коллекции выдается замоканый update
		when(update.message()).thenReturn(message); 		// при любом вызове метода выдается замоканый message
		when(message.text()).thenReturn("/start"); 		// при обращении к тексту выдается строка для проверки
		when(message.chat()).thenReturn(chat); 				// при вызове выдается замоканый chat
		when(chat.id()).thenReturn(chatId);					//при обращении выдается Long

		service.process(updates); 							//запускаю метод
		Mockito.verify(bot).execute(messageTest); 			//проверяю что метод был вызван с параметром messageTest
	}

}
