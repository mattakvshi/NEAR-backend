package ru.mattakvshi.near.service;

import jakarta.transaction.Transactional;

import java.util.UUID;

public interface SubsService {
    @Transactional
    void subscribeUserToCommunity(UUID userId, UUID communityId);

    @Transactional
    void cancelSubscriptionUserToCommunity(UUID userId, UUID communityId);
}
