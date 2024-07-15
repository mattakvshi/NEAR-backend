package ru.mattakvshi.near.service;

import java.util.UUID;

public interface FriendsService {
    void friendRequest(UUID userId, UUID friendId);

    void addNewFriend(UUID userId, UUID friendId);

    void rejectFriendsRequest(UUID userId, UUID friendId);

    void deleteFriend(UUID userId, UUID friendId);
}
