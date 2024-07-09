package ru.mattakvshi.TelegramWorker.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mattakvshi.TelegramWorker.config.TelegramBotConfiguration;
import ru.mattakvshi.TelegramWorker.dao.TelegramInfoDAO;
import ru.mattakvshi.TelegramWorker.dto.TelegramMessage;
import ru.mattakvshi.TelegramWorker.entity.TelegramUserInfo;
import ru.mattakvshi.TelegramWorker.entity.TelegramUserInfoId;

//TelegramWebhookBot - более сложное API где бот реагирует на каждое новое сообщение от пользователя, в отличие от используемого, которое само через время проверяет не написали ли

@Service
@Log
public class TelegramBotServiceImpl extends TelegramLongPollingBot implements TelegramBotService {

    @Autowired
    private TelegramBotConfiguration telegramBotConfiguration;

    @Autowired
    private TelegramInfoDAO telegramInfoDAO;

    @Override
    public String getBotUsername() {
        return telegramBotConfiguration.getBotName();
    }

    @Override
    public String getBotToken(){
        return telegramBotConfiguration.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
                // Получаем chat_id из сообщения

                

                var chatId = update.getMessage().getChatId().toString();
                var userName = update.getMessage().getFrom().getUserName();
                var phoneNumber = update.getMessage().getContact().getPhoneNumber();

                if(update.getMessage().getText().equals("/start")) {

                    TelegramUserInfoId id = new TelegramUserInfoId(userName, phoneNumber);
                    TelegramUserInfo telegramUserInfo = new TelegramUserInfo();
                    telegramUserInfo.setId(id);
                    telegramUserInfo.setChatId(chatId);

                    telegramInfoDAO.saveUserInfo(telegramUserInfo);

                    sendMessage("Отправив сообщение боту вы дали разрешение, на отправление вам сообщений из рассылок на которые вы подписаны в NEAR!", chatId);
                } else {
                    sendMessage("Нет необходимости писать сюда что-то лишнее не в рамках команд, ведь это никем не будет прочтено, я просто бот.", chatId);
                }
        }
    }

    @Override
    public void sendNotificationMessage(TelegramMessage message) {
        TelegramUserInfoId telegramUserInfoId = new TelegramUserInfoId();
        telegramUserInfoId.setUserName(message.getShortName());
        telegramUserInfoId.setPhoneNumber(message.getPhoneNumber());

        var chartId = getChatId(telegramUserInfoId);

        sendMessage(message.toString(), chartId);
    }


    private void sendMessage(String messageText, String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);
        try {
            execute(message); // Отправка сообщения
        } catch (TelegramApiException e) {
            log.info("Error occurred: " + e.getMessage());
        }
    }

    private String getChatId(TelegramUserInfoId id){
        TelegramUserInfo telegramUserInfo = telegramInfoDAO.getUserInfoById(id);
        return telegramUserInfo.getChatId();
    }

}
