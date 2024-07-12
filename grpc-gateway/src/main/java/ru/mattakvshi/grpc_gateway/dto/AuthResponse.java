package ru.mattakvshi.grpc_gateway.dto;

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

    public static AuthResponse.Builder newBuilder() {
        return new AuthResponse.Builder();
    }

    public static class Builder {
        private String accessToken;
        private String refreshToken;
        private UUID uuid;

        public Builder setAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Builder setUuid(String uuid) {
            this.uuid = UUID.fromString(uuid);
            return this;
        }

        public AuthResponse build() {
            return new AuthResponse( this.accessToken, this.refreshToken, this.uuid);
        }
    }
}

