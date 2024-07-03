package ru.mattakvshi.near.utils;

import org.springframework.stereotype.Component;
import ru.mattakvshi.near.dto.actions.NotificationTemplateRequest;
import ru.mattakvshi.near.entity.NotificationTemplate;
import ru.mattakvshi.near.entity.User;
import ru.mattakvshi.near.entity.auth.UserAccount;


public class NotificationTemplateBuilder {

    public static NotificationTemplate from(User owner, NotificationTemplateRequest notificationTemplateRequest) {

        NotificationTemplate notificationTemplate = new NotificationTemplate();
        notificationTemplate.setTemplateName(notificationTemplateRequest.getTemplateName());
        notificationTemplate.setOwner(owner);

        notificationTemplate.setMessage(notificationTemplateRequest.getMessage());
        notificationTemplate.setEmergencyType(notificationTemplateRequest.getEmergencyType());

        return notificationTemplate;
    }
}
