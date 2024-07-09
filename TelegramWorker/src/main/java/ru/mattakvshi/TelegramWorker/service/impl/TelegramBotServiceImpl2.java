package ru.mattakvshi.TelegramWorker.service.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mattakvshi.TelegramWorker.config.TelegramBotConfiguration;
import ru.mattakvshi.TelegramWorker.dao.TelegramInfoDAO;
import ru.mattakvshi.TelegramWorker.dto.TelegramMessage;
import ru.mattakvshi.TelegramWorker.entity.TelegramUserInfo;
import ru.mattakvshi.TelegramWorker.entity.TelegramUserInfoId;
import ru.mattakvshi.TelegramWorker.service.TelegramBotService;


@Log
@Service
//@Primary
public class TelegramBotServiceImpl2 extends TelegramWebhookBot implements TelegramBotService {

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
    public String getBotPath(){
        // Верните путь вебхука, который вы настроили для бота
        return "path_to_your_webhook";
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        log.info("Что-то пришло");
        if (update.hasMessage() && update.getMessage().hasText()) {
            var chatId = update.getMessage().getChatId().toString();
            var messageText = update.getMessage().getText();

            if(messageText.equals("/start")) {
                var userName = update.getMessage().getFrom().getUserName();
                var phoneNumber = update.getMessage().getContact().getPhoneNumber();

                TelegramUserInfoId id = new TelegramUserInfoId(userName, phoneNumber);
                TelegramUserInfo telegramUserInfo = new TelegramUserInfo();
                telegramUserInfo.setId(id);
                telegramUserInfo.setChatId(chatId);

                telegramInfoDAO.saveUserInfo(telegramUserInfo);

                return new SendMessage(chatId, "Отправив сообщение боту вы дали разрешение, на отправление вам сообщений из рассылок на которые вы подписаны в NEAR!");
            } else {
                return new SendMessage(chatId, "Нет необходимости писать сюда что-то лишнее не в рамках команд, ведь это никем не будет прочтено, я просто бот.");
            }
        }
        return null;
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