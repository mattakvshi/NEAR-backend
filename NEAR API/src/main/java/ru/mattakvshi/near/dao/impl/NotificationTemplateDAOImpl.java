package ru.mattakvshi.near.dao.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ru.mattakvshi.near.dao.NotificationTemplateDAO;
import ru.mattakvshi.near.dao.repository.NotificationTemplateRepository;
import ru.mattakvshi.near.entity.NotificationTemplate;

import java.util.UUID;

@Log
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
    public UUID updateTemplate(NotificationTemplate template, NotificationTemplate existingTemplate) {
        existingTemplate.setTemplateName(template.getTemplateName());
        existingTemplate.setMessage(template.getMessage());
        existingTemplate.setEmergencyType(template.getEmergencyType());
        return notificationTemplateRepository.save(existingTemplate).getId();
    }

    @Override
    public void deleteTemplate(NotificationTemplate template, NotificationTemplate existingTemplate) {
        existingTemplate.setTemplateName(template.getTemplateName());
        existingTemplate.setMessage(template.getMessage());
        existingTemplate.setOwner(template.getOwner());
        existingTemplate.setEmergencyType(template.getEmergencyType());
        notificationTemplateRepository.delete(existingTemplate);
    }

    @Override
    public NotificationTemplate findById(UUID templateId) {
        return notificationTemplateRepository.findById(templateId)
                .orElseThrow(() -> new EntityNotFoundException("Template with ID " + templateId + " not found."));
    }


}
