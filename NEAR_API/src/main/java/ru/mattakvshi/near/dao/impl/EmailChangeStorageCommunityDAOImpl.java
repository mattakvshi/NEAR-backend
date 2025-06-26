package ru.mattakvshi.near.dao.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.mattakvshi.near.dao.EmailChangeStorageCommunityDAO;
import ru.mattakvshi.near.dao.repository.EmailChangeStorageCommunityRepository;
import ru.mattakvshi.near.entity.auth.EmailChangeStorageCommunity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class EmailChangeStorageCommunityDAOImpl implements EmailChangeStorageCommunityDAO {
    @Autowired
    EmailChangeStorageCommunityRepository emailChangeStorageCommunityRepository;

    @Transactional
    @Override
    public void save(EmailChangeStorageCommunity emailChangeStorageCommunity){
        emailChangeStorageCommunityRepository.save(emailChangeStorageCommunity);
    }

    @Transactional
    @Override
    public void delete(EmailChangeStorageCommunity emailChangeStorageCommunity){
        emailChangeStorageCommunityRepository.delete(emailChangeStorageCommunity);
    }

    @Override
    public EmailChangeStorageCommunity findById(UUID token){
        return emailChangeStorageCommunityRepository.findById(token).orElse(null);
    }

    @Override
    public List<EmailChangeStorageCommunity> findAllByExpiryDateBefore(LocalDateTime now){
        return emailChangeStorageCommunityRepository.findAllByExpiryDateBefore(now);
    }

    @Override
    public void deleteAll(List<EmailChangeStorageCommunity> expired){
        emailChangeStorageCommunityRepository.deleteAll(expired);
    }

    @Scheduled(fixedRate = 86400000) // Раз в сутки
    public void deleteExpiredEmailChangeRequests() {
        LocalDateTime now = LocalDateTime.now();
        List<EmailChangeStorageCommunity> expired = findAllByExpiryDateBefore(now);
        deleteAll(expired);
    }
}
