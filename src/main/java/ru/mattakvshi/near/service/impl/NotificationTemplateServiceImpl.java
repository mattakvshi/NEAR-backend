package ru.mattakvshi.near.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dao.NotificationTemplateDAO;
import ru.mattakvshi.near.entity.NotificationTemplate;
import ru.mattakvshi.near.service.NotificationTemplateService;

import java.util.UUID;

@Service
public class NotificationTemplateServiceImpl implements NotificationTemplateService {

    @Autowired
    private NotificationTemplateDAO notificationTemplateDAO;

    @Override
    public UUID saveTemplate(NotificationTemplate notificationTemplate){
        return notificationTemplateDAO.saveTemplate(notificationTemplate);
    }

    @Override
    public UUID updateTemplate(NotificationTemplate notificationTemplate, UUID templateId) {
        return notificationTemplateDAO.updateTemplate(notificationTemplate, templateId);
    }

    @Override
    public void deleteTemplate(NotificationTemplate notificationTemplate, UUID templateId) {
        notificationTemplateDAO.deleteTemplate(notificationTemplate, templateId);
    }

}
