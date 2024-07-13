package ru.mattakvshi.near.service;


import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import ru.mattakvshi.near.dto.actions.template.SendTemplateRequest;
import ru.mattakvshi.near.entity.NotificationTemplate;

import java.util.UUID;

public interface NotificationTemplateService {
    @Transactional
    UUID saveTemplate(NotificationTemplate notificationTemplate);

    @Transactional
    UUID updateTemplate(NotificationTemplate notificationTemplate, UUID templateId);

    @Transactional
    void deleteTemplate(NotificationTemplate notificationTemplate, UUID templateId);

    void sendTemplate(SendTemplateRequest sendTemplateRequest, Authentication account);
}
