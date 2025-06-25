package ru.mattakvshi.near.service.impl.auth;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.service.MailSender;


@Log
@Service
public class MailSenderImpl implements MailSender {

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void send(String emailTo, String subject, String body) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        log.info(emailTo);

        if (emailTo != null) {
            mailSender.send(mailMessage);
        } else {
            throw new RuntimeException("Email Sender Failed");
        }
    }
}
