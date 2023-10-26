package pro.sky.telegrambot;

import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.repository.NotificationRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TelegramBotApplicationTests {

	@Mock
	private NotificationRepository repository;

	@Mock
	private Update update;

	@InjectMocks
	TelegramBotUpdatesListener service;

	@Test
	void processStartTest() {

	}

}
