package ru.mattakvshi.TelegramWorker.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class TelegramBotConfiguration {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.key}")
    private String botToken;
}
