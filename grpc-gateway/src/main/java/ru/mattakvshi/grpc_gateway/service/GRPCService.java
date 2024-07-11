package ru.mattakvshi.grpc_gateway.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mattakvshi.grpc_gateway.dto.AuthRequests;
import ru.mattakvshi.grpccommon.*;
import ru.mattakvshi.grpccommon.UserAuthorizationServiceGrpc;

@GrpcService
public class GRPCService extends UserAuthorizationServiceGrpc.UserAuthorizationServiceImplBase{

    @Autowired
    private UserAuthorizationClientService userAuthorizationClientService;

    @Override
    public void authUser(AuthRequest request, StreamObserver<ru.mattakvshi.grpccommon.AuthResponse> responseObserver) {
        AuthRequests authRequests = new AuthRequests();
        authRequests.setEmail(request.getEmail());
        authRequests.setPassword(request.getPassword());

        // Вызов REST-сервиса и отправка ответа клиенту gRPC
        userAuthorizationClientService.authUser(authRequests).subscribe(authResponse -> {
            AuthResponse response = AuthResponse.newBuilder()
                    .setType("Bearer")
                    .setAccessToken(authResponse.getAccessToken())
                    .setRefreshToken(authResponse.getRefreshToken())
                    .setUuid(authResponse.getUuid().toString())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        });
    }

    @Override
    public void getNewAccessToken(RefreshJwtRequest request, StreamObserver<AuthResponse> responseObserver) {
        RefreshJwtRequest refreshRequest = new RefreshJwtRequest();
        refreshRequest.setRefreshToken(request.getRefreshToken());

        // Вызов REST-сервиса и отправка ответа клиенту gRPC
        userAuthorizationClientService.getNewAccessTokenForUser(refreshRequest).subscribe(authResponse -> {
            AuthResponse response = AuthResponse.newBuilder()
                    .setType("Bearer")
                    .setAccessToken(authResponse.getAccessToken())
                    .setRefreshToken(authResponse.getRefreshToken())
                    .setUuid(authResponse.getUuid().toString())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        });
    }

    @Override
    public void getNewRefreshToken(RefreshJwtRequest request, StreamObserver<AuthResponse> responseObserver) {
        RefreshJwtRequest refreshRequest = new RefreshJwtRequest();
        refreshRequest.setRefreshToken(request.getRefreshToken());

        // Вызов REST-сервиса и отправка ответа клиенту gRPC
        userAuthorizationClientService.getNewRefreshTokenForUser(refreshRequest).subscribe(authResponse -> {
            AuthResponse response = AuthResponse.newBuilder()
                    .setType("Bearer")
                    .setAccessToken(authResponse.getAccessToken())
                    .setRefreshToken(authResponse.getRefreshToken())
                    .setUuid(authResponse.getUuid().toString())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        });
    }

    @Override
    public void getCurrentUser(google.protobuf.Empty request, StreamObserver<UserResponse> responseObserver) {
        // Вызов REST-сервиса и отправка ответа клиенту gRPC
        userAuthorizationClientService.getCurrentUser().subscribe(user -> {
            UserResponse response = UserResponse.newBuilder()
                    .setPrincipal(user.toString()) // Здесь предполагается, что REST-сервис возвращает principal в виде строки
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        });
    }
}
