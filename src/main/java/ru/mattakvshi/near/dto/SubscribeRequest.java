package ru.mattakvshi.near.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SubscribeRequest {

    private UUID userId;

    private UUID communityId;
}
