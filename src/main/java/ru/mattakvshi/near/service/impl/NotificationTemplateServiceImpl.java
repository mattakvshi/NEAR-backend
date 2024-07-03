package ru.mattakvshi.near.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.entity.NotificationTemplate;
import ru.mattakvshi.near.service.NotificationTemplateService;

import java.util.UUID;

@Service
public class NotificationTemplateServiceImpl implements NotificationTemplateService {
    @Override
    public UUID saveTemplate(NotificationTemplate notificationTemplate) {
        return null;
    }
}
