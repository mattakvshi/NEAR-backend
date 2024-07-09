package ru.mattakvshi.TelegramWorker.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.mattakvshi.TelegramWorker.config.TelegramBotConfiguration;
import ru.mattakvshi.TelegramWorker.dao.TelegramInfoDAO;
import ru.mattakvshi.TelegramWorker.dto.TelegramMessage;
import ru.mattakvshi.TelegramWorker.entity.TelegramUserInfo;
import ru.mattakvshi.TelegramWorker.entity.TelegramUserInfoId;
import ru.mattakvshi.TelegramWorker.service.TelegramBotService;

import java.util.ArrayList;
import java.util.List;

//TelegramWebhookBot - более сложное API где бот реагирует на каждое новое сообщение от пользователя, в отличие от используемого, которое само через время проверяет не написали ли
@Log
@Service
@Primary
public class TelegramBotServiceImpl extends TelegramLongPollingBot implements TelegramBotService {

    private final TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

    @Autowired
    private TelegramBotConfiguration telegramBotConfiguration;

    @Autowired
    private TelegramInfoDAO telegramInfoDAO;

    @PostConstruct
    private void init() throws TelegramApiException {
        telegramBotsApi.registerBot(this); // Регистрируем бота
    }

    public TelegramBotServiceImpl() throws TelegramApiException {}

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

        if(update.hasMessage() && update.getMessage().hasContact()) {
            var chatId = update.getMessage().getChatId().toString();
            var userName = update.getMessage().getFrom().getUserName();
            var phoneNumber = update.getMessage().getContact().getPhoneNumber();

            log.info(update.getMessage().getText());

            TelegramUserInfoId id = new TelegramUserInfoId(userName, phoneNumber);
            TelegramUserInfo telegramUserInfo = new TelegramUserInfo();
            telegramUserInfo.setId(id);
            telegramUserInfo.setChatId(chatId);

            telegramInfoDAO.saveUserInfo(telegramUserInfo);

            sendMessage("Отправив сообщение боту вы дали разрешение, на отправление вам сообщений из рассылок на которые вы подписаны в NEAR!", chatId);
        }

        if (update.hasMessage() && update.getMessage().hasText()) {

            var chatId = update.getMessage().getChatId().toString();

            switch (update.getMessage().getText()) {
                case "/start" -> {
                    getContactData(update); // Запросили разрешения на получение номера телефона
                }
                default -> sendMessage("Нет необходимости писать сюда что-то лишнее не в рамках команд, ведь это никем не будет прочтено, я просто бот.", chatId);
            }
        }
    }

    @Override
    public void sendNotificationMessage(TelegramMessage message) {
            if (message.getShortName() != null && message.getPhoneNumber() != null) {

            TelegramUserInfoId telegramUserInfoId = new TelegramUserInfoId();
            telegramUserInfoId.setUserName(message.getShortName());
            telegramUserInfoId.setPhoneNumber(message.getPhoneNumber());

            log.info(telegramUserInfoId.toString());

            var chartId = getChatId(telegramUserInfoId);

            sendMessage(message.toString(), chartId);
        } else {
                throw new NullPointerException("Отсутствуют данные для поиска!");
            }
    }


    private void sendMessage(String messageText, String chatId){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);
        try {
            execute(message); // Отправка сообщения
        } catch (TelegramApiException e) {
            log.info("Error occurred: " + e.getMessage());
        }
    }

    private void getContactData(Update update){
        // Создаем клавиатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Создаем строку для кнопки запроса контакта
        KeyboardRow contactRow = new KeyboardRow();
        KeyboardButton contactButton = new KeyboardButton("Отправить мой номер телефона");
        contactButton.setRequestContact(true);
        contactRow.add(contactButton);
        keyboard.add(contactRow);

        // Устанавливаем свойства клавиатуры
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        // Отправляем сообщение с клавиатурой
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        message.setText("Пожалуйста, поделитесь своим номером телефона.");
        message.setReplyMarkup(replyKeyboardMarkup);
        try {
            execute(message); // Не забудьте выполнить отправку сообщения
        } catch (TelegramApiException e) {
            log.info("Error occurred: " + e.getMessage());
        }
    }



    private String getChatId(TelegramUserInfoId id){
        TelegramUserInfo telegramUserInfo = telegramInfoDAO.getUserInfoById(id);
        log.info(telegramUserInfo.toString());
        return telegramUserInfo.getChatId();
    }

}
