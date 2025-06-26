package ru.mattakvshi.near.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mattakvshi.near.entity.auth.EmailChangeStorage;
import ru.mattakvshi.near.entity.auth.EmailChangeStorageCommunity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface EmailChangeStorageCommunityRepository extends CrudRepository<EmailChangeStorageCommunity, UUID> {
    List<EmailChangeStorageCommunity> findAllByExpiryDateBefore(LocalDateTime now);
}
