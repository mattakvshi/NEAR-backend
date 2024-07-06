package ru.mattakvshi.near.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dao.NotificationTemplateDAO;
import ru.mattakvshi.near.dao.UserDAO;
import ru.mattakvshi.near.dto.actions.SendTemplateRequest;
import ru.mattakvshi.near.entity.NotificationTemplate;
import ru.mattakvshi.near.entity.base.User;
import ru.mattakvshi.near.service.NotificationTemplateService;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationTemplateServiceImpl implements NotificationTemplateService {

    @Autowired
    private NotificationTemplateDAO notificationTemplateDAO;

    @Autowired
    private UserDAO userDAO;

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

    @Override
    public void sendTemplate(SendTemplateRequest sendTemplateRequest) {
        NotificationTemplate notificationTemplate = notificationTemplateDAO.findById(sendTemplateRequest.getTemplateId());
        List<User> recipients = userDAO.findAllById(sendTemplateRequest.getRecipients());

    }

}
