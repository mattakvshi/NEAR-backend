package ru.mattakvshi.grpc_gateway.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshJwtRequest {

    public String refreshToken;

    public static RefreshJwtRequest.Builder newBuilder() {
        return new RefreshJwtRequest.Builder();
    }

    public static class Builder {
        private String refreshToken;

        public Builder setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public RefreshJwtRequest build() {
            RefreshJwtRequest refreshJwtRequest = new RefreshJwtRequest();
            refreshJwtRequest.setRefreshToken(this.refreshToken);
            return refreshJwtRequest;
        }
    }

}