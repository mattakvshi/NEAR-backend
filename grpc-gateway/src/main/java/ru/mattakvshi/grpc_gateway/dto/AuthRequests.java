package ru.mattakvshi.grpc_gateway.dto;

import lombok.Data;

@Data
public class AuthRequests {

    private String email;

    private String password;

    public static AuthRequests.Builder newBuilder() {
        return new AuthRequests.Builder();
    }

    public static class Builder {
        private String email;
        private String password;

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public AuthRequests build() {
            AuthRequests authRequests = new AuthRequests();
            authRequests.setEmail(this.email);
            authRequests.setPassword(this.password);
            return authRequests;
        }
    }

}
