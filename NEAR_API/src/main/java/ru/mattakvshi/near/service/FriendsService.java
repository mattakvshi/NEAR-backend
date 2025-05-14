package ru.mattakvshi.near.service;

import ru.mattakvshi.near.dto.user.FriendsForUserDTO;

import java.util.UUID;

public interface FriendsService {

    FriendsForUserDTO getFriendsData(UUID userId);

    void friendRequest(UUID userId, UUID friendId);

    void addNewFriend(UUID userId, UUID friendId);

    void rejectFriendsRequest(UUID userId, UUID friendId);

    void deleteFriend(UUID userId, UUID friendId);
}
