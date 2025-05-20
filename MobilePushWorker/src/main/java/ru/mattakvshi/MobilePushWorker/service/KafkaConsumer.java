package ru.mattakvshi.MobilePushWorker.service;

import lombok.extern.java.Log;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.mattakvshi.MobilePushWorker.dto.PushMessage;

@Service
@Log
public class KafkaConsumer {

    private final NotificationSender notificationSender;

    public KafkaConsumer(NotificationSender notificationSender) {
        this.notificationSender = notificationSender;
    }

    @KafkaListener(topics = "Mobile_Notification", groupId = "mobile_consumer")
    public void listen(PushMessage message) {
        log.info("Получено сообщение: " + message);

        // Делегируем отправку уведомления
        notificationSender.sendNotification(
                message.getDeviceToken(),
                message.getTemplateName(),
                message.getMessage() + ", " + message.getOwner() + ", " + message.getEmergencyType()
        );
    }
}
