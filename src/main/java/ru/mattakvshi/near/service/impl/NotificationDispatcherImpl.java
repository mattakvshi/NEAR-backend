package ru.mattakvshi.near.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dto.notice.NotificationMessage;
import ru.mattakvshi.near.entity.NotificationOptions;
import ru.mattakvshi.near.entity.NotificationTemplate;
import ru.mattakvshi.near.entity.base.User;
import ru.mattakvshi.near.service.NotificationDispatcher;

import java.util.List;

@Service
public class NotificationDispatcherImpl implements NotificationDispatcher {

    @Autowired
    private KafkaTemplate<String, NotificationMessage> kafkaTemplate;

    @Override
    public void dispatch(NotificationTemplate notificationTemplate, List<User> recipients) {
        for (User recipient : recipients) {
            // Определение опций уведомлений для пользователя
            List<NotificationOptions> userOptions = recipient.getSelectedOptions();
            // Отправка сообщений в соответствующие топики или партиции
            userOptions.forEach(option -> {
                NotificationMessage message = createMessage(notificationTemplate, recipient, option);
                // Отправка сообщения в топик, соответствующий типу уведомления
                kafkaTemplate.send(option.getKafkaTopic(), message);
            });
        }
    }

    private NotificationMessage createMessage(NotificationTemplate template, User recipient, NotificationOptions option) {
        // Создание сообщения для Kafka на основе шаблона уведомления, получателя и выбранного варианта рассылки
        NotificationMessage message = new NotificationMessage();
        message.setTemplateId(template.getId());
        message.setRecipientId(recipient.getId());
        message.setOptionId(option.getId());
        // Здесь можно добавить дополнительные данные, необходимые для обработки сообщения
        return message;
    }

}
