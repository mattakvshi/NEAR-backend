package ru.mattakvshi.MobilePushWorker.service;

import lombok.extern.java.Log;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.mattakvshi.MobilePushWorker.dto.NotificationMessage;
import ru.mattakvshi.MobilePushWorker.dto.PushMessage;

@Service
@Log
public class KafkaConsumer {

    @KafkaListener(topics = "Mobile_Notification", groupId = "mobile_consumer")
    public void listen(NotificationMessage message) {
        log.info(message.toString());
    }
}
