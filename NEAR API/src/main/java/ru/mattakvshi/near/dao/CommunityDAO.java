package ru.mattakvshi.near.dao;

import jakarta.transaction.Transactional;
import ru.mattakvshi.near.entity.base.Community;

import java.util.UUID;

public interface CommunityDAO {
    @Transactional
    UUID saveCommunity(Community community);

    Community findById(UUID id);
}
