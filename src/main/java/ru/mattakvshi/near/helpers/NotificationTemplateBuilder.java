package ru.mattakvshi.near.helpers;

import org.springframework.security.core.userdetails.UserDetails;
import ru.mattakvshi.near.dto.actions.NotificationTemplateRequest;
import ru.mattakvshi.near.entity.NotificationTemplate;
import ru.mattakvshi.near.entity.User;


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
