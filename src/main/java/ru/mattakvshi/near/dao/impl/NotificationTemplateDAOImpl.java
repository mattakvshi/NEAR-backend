package ru.mattakvshi.near.dao.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mattakvshi.near.dao.NotificationTemplateDAO;
import ru.mattakvshi.near.dao.repository.NotificationTemplateRepository;
import ru.mattakvshi.near.entity.NotificationTemplate;

import java.util.UUID;

@Component
public class NotificationTemplateDAOImpl implements NotificationTemplateDAO {

    @Autowired
    private NotificationTemplateRepository notificationTemplateRepository;


    @Override
    @Transactional
    public UUID saveTemplate(NotificationTemplate template) {
        return notificationTemplateRepository.save(template).getId();
    }

    @Override
    public UUID updateTemplate(NotificationTemplate template) {
        return notificationTemplateRepository.save(template).getId();
    }

    @Override
    public void deleteTemplate(NotificationTemplate template) {
        notificationTemplateRepository.delete(template);
    }


}
