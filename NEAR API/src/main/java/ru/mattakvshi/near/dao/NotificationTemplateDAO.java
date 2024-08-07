package ru.mattakvshi.near.dao;

import jakarta.transaction.Transactional;
import ru.mattakvshi.near.entity.NotificationTemplate;

import java.util.UUID;

public interface NotificationTemplateDAO {
    @Transactional
    UUID saveTemplate(NotificationTemplate template);

    @Transactional
    UUID updateTemplate(NotificationTemplate template, NotificationTemplate existingTemplate);

    @Transactional
    void deleteTemplate(NotificationTemplate template, NotificationTemplate existingTemplate);

    NotificationTemplate findById(UUID templateId);
}
