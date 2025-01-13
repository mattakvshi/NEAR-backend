package ru.mattakvshi.grpc_gateway.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.mattakvshi.grpc_gateway.dto.AuthRequests;
import ru.mattakvshi.grpc_gateway.dto.AuthResponse;
import ru.mattakvshi.grpc_gateway.dto.RefreshJwtRequest;
import ru.mattakvshi.grpc_gateway.dto.user.UserDTO;
import ru.mattakvshi.grpc_gateway.service.UserAuthorizationClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@Slf4j
@Service
public class UserAuthorizationClientServiceImpl implements UserAuthorizationClientService {

    @Value("${gateway.grpc.url}") String mainUrl;

    private final WebClient webClient;


    @Autowired
    public UserAuthorizationClientServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(mainUrl).build();
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
    public Mono<UserDTO> getCurrentUser(String accessToken) {
        return webClient.get()
                .uri("/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            try {
                UserDTO userDTO = objectMapper.readValue(response, UserDTO.class);
                return Mono.just(userDTO);
            } catch (Exception e) {
                return Mono.error(e);
            }
        });
    }
}