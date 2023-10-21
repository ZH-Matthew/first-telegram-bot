package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Notification;
import pro.sky.telegrambot.repository.NotificationRepository;
import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    NotificationRepository notificationRepository;

    public TelegramBotUpdatesListener(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String userMessage = update.message().text();
            Long chatId = update.message().chat().id();

            //проверка на /start
            if (userMessage.equals("/start")){
                String messageText = "О! Привет!";
                SendMessage message = new SendMessage(chatId, messageText);
                SendResponse response = telegramBot.execute(message);
                response.isOk();
                response.errorCode();
            }

            //парсинг сообщения и сохранение в БД
            Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");  //создали паттерн (условие поиска)
            Matcher matcher = pattern.matcher(userMessage);                                 //проверили сообщение по условию (паттерну) на соответствие
            if (matcher.matches()) {                                                        //если условие удовлетворено
                String date = matcher.group(1);                                             //разделили сообщение по частям
                LocalDateTime notification_send_time = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                String notification_message = matcher.group(3); //!!! узнать почему тут именно цифра 3

                Notification notification = new Notification();
                notification.setNotification(notification_message);
                notification.setDateTime(notification_send_time);
                notification.setChat_id(chatId);
                notificationRepository.save(notification);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Scheduled(cron = "0 * * ? * *")
    public void findNotificationsByDateTime() {
        List<Notification> notificationsNow = notificationRepository.findByDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        notificationsNow.forEach(notification -> {
            SendMessage message = new SendMessage(notification.getChat_id(), notification.getNotification());
            SendResponse response = telegramBot.execute(message);
            response.isOk();
        });
    }

}
