package ru.mattakvshi.grpc_gateway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.mattakvshi.grpc_gateway.dto.AuthRequests;
import ru.mattakvshi.grpc_gateway.dto.AuthResponse;
import ru.mattakvshi.grpc_gateway.dto.RefreshJwtRequest;
import ru.mattakvshi.grpc_gateway.service.UserAuthorizationClientService;

@Service
public class UserAuthorizationClientServiceImpl implements UserAuthorizationClientService {

    private final WebClient webClient;

    @Autowired
    public UserAuthorizationClientServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/NEAR").build();
    }

    @Override
    public Mono<AuthResponse> authUser(AuthRequests authRequest) {
        return webClient.post()
                .uri("/login/account")
                .bodyValue(authRequest)
                .retrieve()
                .bodyToMono(AuthResponse.class);
    }

    @Override
    public Mono<AuthResponse> getNewAccessTokenForUser(RefreshJwtRequest refreshJwtRequest) {
        return webClient.post()
                .uri("/token/account")
                .bodyValue(refreshJwtRequest)
                .retrieve()
                .bodyToMono(AuthResponse.class);
    }

    @Override
    public Mono<AuthResponse> getNewRefreshTokenForUser(RefreshJwtRequest refreshJwtRequest) {
        return webClient.post()
                .uri("/user/refresh")
                .bodyValue(refreshJwtRequest)
                .retrieve()
                .bodyToMono(AuthResponse.class);
    }

    @Override
    public Mono<Object> getCurrentUser() {
        return webClient.get()
                .uri("/user/me")
                .retrieve()
                .bodyToMono(Object.class);
    }
}