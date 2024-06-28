package ru.mattakvshi.near.service;

import ru.mattakvshi.near.entity.Community;

import java.util.UUID;

public interface CommunityService {
    UUID saveCommunity(Community community);
}
