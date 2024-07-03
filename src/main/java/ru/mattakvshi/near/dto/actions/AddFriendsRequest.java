package ru.mattakvshi.near.dto.actions;

import lombok.Data;

import java.util.UUID;

@Data
public class AddFriendsRequest {

    //private UUID userId;

    private UUID friendId;
}
