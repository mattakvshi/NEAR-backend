package ru.mattakvshi.near.service;

import jakarta.transaction.Transactional;
import ru.mattakvshi.near.entity.base.User;

import java.util.UUID;

public interface UserService {

    @Transactional
    UUID saveUserForFirstTime(User user);

    @Transactional
    UUID saveUser(User user);

    @Transactional
    void subscribeUserToCommunity(UUID userId, UUID communityId);

    @Transactional
    void cancelSubscriptionUserToCommunity(UUID userId, UUID communityId);

    @Transactional
    void friendRequest(UUID userId, UUID friendId);

    @Transactional
    void addNewFriend(UUID userId, UUID friendId);

    @Transactional
    void rejectFriendsRequest(UUID userId, UUID friendId);

    @Transactional
    void deleteFriend(UUID userId, UUID friendId);

    User getUser(UUID userId);
}
