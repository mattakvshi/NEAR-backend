package ru.mattakvshi.TelegramWorker.service;

import lombok.extern.java.Log;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.mattakvshi.TelegramWorker.dto.NotificationMessage;
import ru.mattakvshi.TelegramWorker.dto.TelegramMessage;

@Service
@Log
public class KafkaConsumer {

    @KafkaListener(topics = "Telegram", groupId = "telegram_consumer")
    public void listen(NotificationMessage message) {
        TelegramMessage telegramMessage = (TelegramMessage) message;
        log.info(telegramMessage.toString());
    }
}
