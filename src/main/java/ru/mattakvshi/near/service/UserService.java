package ru.mattakvshi.near.service;

import jakarta.transaction.Transactional;
import ru.mattakvshi.near.entity.User;

import java.util.UUID;

public interface UserService {

    @Transactional
    UUID saveUserForFirstTime(User user);

    UUID saveUser(User user);

    @Transactional
    void subscribeUserToCommunity(UUID userId, UUID communityId);

    @Transactional
    void cancelSubscriptionUserToCommunity(UUID userId, UUID communityId);

    @Transactional
    void addNewFriend(UUID userId, UUID friend1Id);
}
