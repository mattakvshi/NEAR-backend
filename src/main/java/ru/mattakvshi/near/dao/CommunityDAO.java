package ru.mattakvshi.near.dao;

import ru.mattakvshi.near.entity.Community;

import java.util.UUID;

public interface CommunityDAO {
    UUID saveCommunity(Community community);
}
