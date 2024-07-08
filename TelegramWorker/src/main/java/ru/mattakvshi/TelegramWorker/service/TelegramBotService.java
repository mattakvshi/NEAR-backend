package ru.mattakvshi.TelegramWorker.service;

import ru.mattakvshi.TelegramWorker.dto.TelegramMessage;

public interface TelegramBotService {
    void sendNotificationMessage(TelegramMessage message);
}
