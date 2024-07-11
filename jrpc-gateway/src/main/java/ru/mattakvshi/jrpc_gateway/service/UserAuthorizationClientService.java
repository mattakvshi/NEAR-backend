package ru.mattakvshi.jrpc_gateway.service;

import reactor.core.publisher.Mono;
import ru.mattakvshi.jrpc_gateway.dto.AuthRequests;
import ru.mattakvshi.jrpc_gateway.dto.AuthResponse;
import ru.mattakvshi.jrpc_gateway.dto.RefreshJwtRequest;

public interface UserAuthorizationClientService {
    Mono<AuthResponse> authUser(AuthRequests authRequest);

    Mono<AuthResponse> getNewAccessTokenForUser(RefreshJwtRequest refreshJwtRequest);

    Mono<AuthResponse> getNewRefreshTokenForUser(RefreshJwtRequest refreshJwtRequest);

    Mono<Object> getCurrentUser();
}
