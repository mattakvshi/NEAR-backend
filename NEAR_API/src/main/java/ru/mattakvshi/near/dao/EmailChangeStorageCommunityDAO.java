package ru.mattakvshi.near.dao;

import jakarta.transaction.Transactional;
import ru.mattakvshi.near.entity.auth.EmailChangeStorage;
import ru.mattakvshi.near.entity.auth.EmailChangeStorageCommunity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EmailChangeStorageCommunityDAO {

    @Transactional
    void save(EmailChangeStorageCommunity emailChangeStorageCommunity);

    @Transactional
    void delete(EmailChangeStorageCommunity emailChangeStorageCommunity);

    EmailChangeStorageCommunity findById(UUID token);

    List<EmailChangeStorageCommunity> findAllByExpiryDateBefore(LocalDateTime now);

    void deleteAll(List<EmailChangeStorageCommunity> expired);
}
