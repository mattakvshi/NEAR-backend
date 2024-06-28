package ru.mattakvshi.near.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String jwtToken;

    private UUID uuid;
}

