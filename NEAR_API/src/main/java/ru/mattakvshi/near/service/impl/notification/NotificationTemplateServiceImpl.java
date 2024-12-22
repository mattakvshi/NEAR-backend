package ru.mattakvshi.near.service.impl.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dao.NotificationTemplateDAO;
import ru.mattakvshi.near.dao.UserDAO;
import ru.mattakvshi.near.dto.actions.template.SendTemplateRequest;
import ru.mattakvshi.near.entity.NotificationTemplate;
import ru.mattakvshi.near.entity.base.User;
import ru.mattakvshi.near.service.NotificationDispatcher;
import ru.mattakvshi.near.service.NotificationTemplateService;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationTemplateServiceImpl implements NotificationTemplateService {

    @Autowired
    private NotificationTemplateDAO notificationTemplateDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    NotificationDispatcher notificationDispatcher;

    @Override
    public UUID saveTemplate(NotificationTemplate notificationTemplate){
        return notificationTemplateDAO.saveTemplate(notificationTemplate);
    }

    @Override
    public UUID updateTemplate(NotificationTemplate notificationTemplate, UUID templateId) {
        NotificationTemplate existingTemplate = notificationTemplateDAO.findById(templateId);
        return notificationTemplateDAO.updateTemplate(notificationTemplate, existingTemplate);
    }

    @Override
    public void deleteTemplate(NotificationTemplate notificationTemplate, UUID templateId) {
        NotificationTemplate existingTemplate = notificationTemplateDAO.findById(templateId);
        notificationTemplateDAO.deleteTemplate(notificationTemplate, existingTemplate);
    }

    @Override
    public void sendTemplate(SendTemplateRequest sendTemplateRequest, Authentication account) {
        NotificationTemplate notificationTemplate = notificationTemplateDAO.findById(sendTemplateRequest.getTemplateId());
        List<User> recipients = userDAO.findAllById(sendTemplateRequest.getRecipients());
        notificationDispatcher.dispatch(notificationTemplate, recipients, account);
    }

}
