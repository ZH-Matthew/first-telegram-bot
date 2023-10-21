package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "notification_task")
public class Notification {
    @Id
    @GeneratedValue
    private Long id;

    private Long chat_id;
    private String notification;
    private LocalDateTime notification_send_time;

    public Notification(Long chat_id, String notification, LocalDateTime notification_send_time) {
        this.chat_id = chat_id;
        this.notification = notification;
        this.notification_send_time = notification_send_time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChat_id() {
        return chat_id;
    }

    public void setChat_id(Long chat_id) {
        this.chat_id = chat_id;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public LocalDateTime getNotification_send_time() {
        return notification_send_time;
    }

    public void setNotification_send_time(LocalDateTime notification_send_time) {
        this.notification_send_time = notification_send_time;
    }
}
