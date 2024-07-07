package ru.mattakvshi.near.dto.actions;

import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Getter
public class AddFriendsRequest {

    private UUID friendId;
}
