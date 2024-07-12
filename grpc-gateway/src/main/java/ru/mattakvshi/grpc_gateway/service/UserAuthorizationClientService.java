package ru.mattakvshi.grpc_gateway.service;

import reactor.core.publisher.Mono;
import ru.mattakvshi.grpc_gateway.dto.AuthRequests;
import ru.mattakvshi.grpc_gateway.dto.AuthResponse;
import ru.mattakvshi.grpc_gateway.dto.RefreshJwtRequest;

public interface UserAuthorizationClientService {
    Mono<AuthResponse> authUser(AuthRequests authRequest);

    Mono<AuthResponse> getNewAccessTokenForUser(RefreshJwtRequest refreshJwtRequest);

    Mono<AuthResponse> getNewRefreshTokenForUser(RefreshJwtRequest refreshJwtRequest);

    String getCurrentUser(String accessToken);
}
