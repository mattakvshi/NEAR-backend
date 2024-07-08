package ru.mattakvshi.TelegramWorker.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.objects.Update;

//TelegramWebhookBot - более сложное API где бот реагирует на каждое новое сообщение от пользователя, в отличие от используемого, которое само через время проверяет не написали ли

@Service
public class TelegramBotService extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "";
    }

    @Override
    public void onUpdateReceived(Update update) {

    }


}
