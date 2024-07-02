package ru.mattakvshi.near.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AuthResponse {

    private final String type = "Bearer";

    private String accessToken;

    private String refreshToken;

    private UUID uuid;
}

