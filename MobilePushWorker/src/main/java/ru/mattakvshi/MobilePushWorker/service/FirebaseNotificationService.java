package ru.mattakvshi.MobilePushWorker.service;


import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import com.google.firebase.messaging.*;

@Service
@Log
public class FirebaseNotificationService implements NotificationSender {
    @Override
    public void sendNotification(String deviceToken, String title, String body) {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setToken(deviceToken)
                .setNotification(notification)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Уведомление отправлено: " + response);
        } catch (FirebaseMessagingException e) {
            log.severe("Ошибка отправки: " + e.getMessage());
        }
    }

}
