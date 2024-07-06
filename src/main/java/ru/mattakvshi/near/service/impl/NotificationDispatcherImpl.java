package ru.mattakvshi.near.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dto.notice.EmailMessage;
import ru.mattakvshi.near.dto.notice.NotificationMessage;
import ru.mattakvshi.near.dto.notice.TelegramMessage;
import ru.mattakvshi.near.entity.NotificationOptions;
import ru.mattakvshi.near.entity.NotificationTemplate;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.entity.base.User;
import ru.mattakvshi.near.service.NotificationDispatcher;
import ru.mattakvshi.near.service.UserAccountService;
import ru.mattakvshi.near.service.UserService;

import java.util.List;

@Service
public class NotificationDispatcherImpl implements NotificationDispatcher {

   @Autowired
   private KafkaTemplate<String, NotificationMessage> kafkaTemplate;

    @Override
    public void dispatch(NotificationTemplate notificationTemplate, List<User> recipients, Authentication account) {
        for (User recipient : recipients) {
            // Определение опций уведомлений для пользователя
            List<NotificationOptions> userOptions = recipient.getSelectedOptions();
            // Отправка сообщений в соответствующие топики или партиции
            userOptions.forEach(option -> {
                NotificationMessage message = createMessage(notificationTemplate, recipient, option, account);
                // Отправка сообщения в топик, соответствующий типу уведомления
                kafkaTemplate.send(option.getKafkaTopic(), message);
            });
        }
    }

    private NotificationMessage createMessage(NotificationTemplate template, User recipient, NotificationOptions option, Authentication account) {
        // Создание сообщения для Kafka на основе шаблона уведомления, получателя и выбранного варианта рассылки
        switch (option.getTitle()) {
            case "Email" -> {
                var message = new EmailMessage();
                message.setTemplateName(template.getTemplateName());
                message.setOwner(account.getName());
                message.setMessage(template.getMessage());
                message.setEmergencyType(template.getEmergencyType().getTitle());

                message.setEmail(recipient.getEmail());

                return message;
            }
            case "Telegram" -> {
                var message = new TelegramMessage();
                message.setTemplateName(template.getTemplateName());
                message.setOwner(account.getName());
                message.setMessage(template.getMessage());
                message.setEmergencyType(template.getEmergencyType().getTitle());

                message.setPhoneNumber(recipient.getTelegramShortName());

                return message;
            }
            default -> throw new IllegalStateException("Unexpected value: " + option.getTitle());
        }

    }

}
