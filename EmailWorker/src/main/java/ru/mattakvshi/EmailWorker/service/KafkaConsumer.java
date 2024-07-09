package ru.mattakvshi.EmailWorker.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.mattakvshi.EmailWorker.dto.EmailMessage;
import ru.mattakvshi.EmailWorker.dto.NotificationMessage;

@Service
@Log
public class KafkaConsumer {

    @Autowired
    private MailSender mailSender;

    @KafkaListener(topics = "Email", groupId = "email_consumer")
    public void listen(NotificationMessage message) {
        EmailMessage emailMessage = (EmailMessage) message;
        log.info(emailMessage.toString());

        mailSender.send(emailMessage.getEmail(), "ВНИМАНИЕ ОПОВЕЩЕНИЕ ОТ NEAR!!!", message.toString());
    }
}
