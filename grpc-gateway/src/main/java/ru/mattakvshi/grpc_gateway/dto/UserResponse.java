package ru.mattakvshi.grpc_gateway.dto;

import lombok.Data;

@Data
public class UserResponse {

    private String principal;

    public static UserResponse.Builder newBuilder() {
        return new UserResponse.Builder();
    }

    public static class Builder {
        private String principal;

        public Builder setPrincipal(String principal) {
            this.principal = principal;
            return this;
        }

        public UserResponse build() {
            UserResponse userResponse = new UserResponse();
            userResponse.setPrincipal(this.principal);
            return userResponse;
        }
    }
}
