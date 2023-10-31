package pro.sky.telegrambot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.repository.NotificationRepository;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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

	@InjectMocks
	TelegramBotUpdatesListener service;

	@Test
	void processStartTest() {

		String answer = "О! Привет!";
		Long chatId = 123L;


		when(update.message()).thenReturn(message); 		// при любом вызове метода выдается замоканый message
		when(message.text()).thenReturn("/start"); 		// при обращении к тексту выдается строка для проверки
		when(message.chat()).thenReturn(chat); 				// при вызове выдается замоканый chat
		when(chat.id()).thenReturn(chatId);					//при обращении выдается Long
		when(bot.execute(any())).thenReturn(Mockito.mock(SendResponse.class)); //при обращении к методу отправки возвращается замоканый SendResponse

		service.process(List.of(update));

		ArgumentCaptor<SendMessage> argument = ArgumentCaptor.forClass(SendMessage.class); //создаем захват аргумента SendMessage
		Mockito.verify(bot).execute(argument.capture()); //захватываем наш аргумент в нужном нам методе через verify
		assertEquals(answer, argument.getValue().getParameters().get("text"));
		assertEquals(chatId, argument.getValue().getParameters().get("chat_id"));
	}
}
