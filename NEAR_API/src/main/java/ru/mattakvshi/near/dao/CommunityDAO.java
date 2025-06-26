package ru.mattakvshi.near.dao;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import ru.mattakvshi.near.entity.base.Community;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface CommunityDAO {
    @Transactional
    UUID saveCommunity(Community community);

    Community findById(UUID id);

    Page<Community> findAllByCommunityNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String nameQuery, String descriptionQuery, Pageable pageable
    );
}
