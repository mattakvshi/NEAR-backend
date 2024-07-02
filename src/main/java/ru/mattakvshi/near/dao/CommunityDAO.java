package ru.mattakvshi.near.dao;

import ru.mattakvshi.near.entity.Community;
import ru.mattakvshi.near.entity.User;

import java.util.UUID;

public interface CommunityDAO {
    UUID saveCommunity(Community community);

    Community findById(UUID id);
}
