package ru.mattakvshi.EmailWorker.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Getter
@Configuration
public class EmailSenderConfiguration {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.protocol}")
    private String protocol;

    @Value("${mail.debug}")
    private String debug;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Здесь вы можете установить свойства, такие как хост, порт, имя пользователя и пароль
        mailSender.setHost(host);
        mailSender.setPort(port);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        // Дополнительные свойства, если необходимо
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", protocol);
        //props.put("mail.smtp.auth", "true");
        //props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", debug);

        return mailSender;
    }
}
