package ru.mattakvshi.near.dto.actions;

import lombok.Data;

import java.util.UUID;

@Data
public class SubscribeRequest {

    private UUID userId;

    private UUID communityId;
}
