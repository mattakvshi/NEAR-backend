package ru.mattakvshi.near.helpers;

import ru.mattakvshi.near.dto.actions.template.NotificationTemplateRequest;
import ru.mattakvshi.near.entity.NotificationTemplate;
import ru.mattakvshi.near.entity.base.TemplateOwner;


public class NotificationTemplateBuilder {

    public static NotificationTemplate from(TemplateOwner owner, NotificationTemplateRequest notificationTemplateRequest) {

        NotificationTemplate notificationTemplate = new NotificationTemplate();
        notificationTemplate.setTemplateName(notificationTemplateRequest.getTemplateName());
        notificationTemplate.setOwner(owner);

        notificationTemplate.setMessage(notificationTemplateRequest.getMessage());
        notificationTemplate.setEmergencyType(notificationTemplateRequest.getEmergencyType());

        return notificationTemplate;
    }
}
