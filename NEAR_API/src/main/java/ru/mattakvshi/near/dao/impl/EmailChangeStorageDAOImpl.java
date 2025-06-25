package ru.mattakvshi.near.dao.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.mattakvshi.near.dao.EmailChangeStorageDAO;
import ru.mattakvshi.near.dao.repository.EmailChangeStorageRepository;
import ru.mattakvshi.near.entity.auth.EmailChangeStorage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class EmailChangeStorageDAOImpl implements EmailChangeStorageDAO {
    @Autowired
    EmailChangeStorageRepository emailChangeStorageRepository;

    @Transactional
    @Override
    public void save(EmailChangeStorage emailChangeStorage){
        emailChangeStorageRepository.save(emailChangeStorage);
    }

    @Transactional
    @Override
    public void delete(EmailChangeStorage emailChangeStorage){
        emailChangeStorageRepository.delete(emailChangeStorage);
    }

    @Override
    public EmailChangeStorage findById(UUID token){
        return emailChangeStorageRepository.findById(token).orElse(null);
    }

    List<EmailChangeStorage> findAllByExpiryDateBefore(LocalDateTime now){
        return emailChangeStorageRepository.findAllByExpiryDateBefore(now);
    }

    void deleteAll(List<EmailChangeStorage> expired){
        emailChangeStorageRepository.deleteAll(expired);
    }

    @Scheduled(fixedRate = 86400000) // Раз в сутки
    public void deleteExpiredEmailChangeRequests() {
        LocalDateTime now = LocalDateTime.now();
        List<EmailChangeStorage> expired = findAllByExpiryDateBefore(now);
        deleteAll(expired);
    }
}
