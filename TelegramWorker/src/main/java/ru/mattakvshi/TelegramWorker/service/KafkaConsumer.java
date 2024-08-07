package ru.mattakvshi.TelegramWorker.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.mattakvshi.TelegramWorker.dto.NotificationMessage;
import ru.mattakvshi.TelegramWorker.dto.TelegramMessage;

@Log
@Service
public class KafkaConsumer {

    @Autowired
    TelegramBotService telegramBotService;

    @KafkaListener(topics = "Telegram", groupId = "telegram_consumer")
    public void listen(NotificationMessage message) {
        TelegramMessage telegramMessage = (TelegramMessage) message;

        log.info(message.toString() + " " + telegramMessage.getPhoneNumber() + " " + telegramMessage.getShortName());

        try {
            telegramBotService.sendNotificationMessage(telegramMessage);
        } catch (Exception e) {
            log.info("Error sending notification message: " + e.getMessage());
        }
        log.info(telegramMessage.toString());
    }
}
