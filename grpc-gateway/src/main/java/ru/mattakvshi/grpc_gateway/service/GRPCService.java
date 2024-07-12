package ru.mattakvshi.grpc_gateway.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.java.Log;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mattakvshi.grpc_gateway.dto.*;
import ru.mattakvshi.grpccommon.UserAuthorizationServiceGrpc;
import ru.mattakvshi.grpc_gateway.mapper.*;

@Log
@GrpcService // Аннотация, указывающая, что этот класс является gRPC сервисом
public class GRPCService extends UserAuthorizationServiceGrpc.UserAuthorizationServiceImplBase {

    @Autowired
    private UserAuthorizationClientService userAuthorizationClientService;

    @Override
    public void authUser(ru.mattakvshi.grpccommon.AuthRequest request, StreamObserver<ru.mattakvshi.grpccommon.AuthResponse> responseObserver) {
        AuthRequests authRequests = new AuthRequests(); // Создание нового объекта AuthRequests
        authRequests.setEmail(request.getEmail()); // Установка email из запроса
        authRequests.setPassword(request.getPassword()); // Установка пароля из запроса

        // Вызов REST-сервиса и отправка ответа клиенту gRPC
        userAuthorizationClientService.authUser(authRequests).subscribe(authResponse -> {
            // Подписка на результат вызова REST-сервиса
//            ru.mattakvshi.grpccommon.AuthResponse response = ru.mattakvshi.grpccommon.AuthResponse.newBuilder() // Создание нового объекта AuthResponse с помощью билдера
//                    .setAccessToken(authResponse.getAccessToken()) // Установка accessToken из ответа REST-сервиса
//                    .setRefreshToken(authResponse.getRefreshToken()) // Установка refreshToken из ответа REST-сервиса
//                    .setUuid(authResponse.getUuid().toString()) // Установка uuid из ответа REST-сервиса
//                    .build(); // Завершение создания объекта AuthResponse

            // Подписка на результат вызова REST-сервиса
            ru.mattakvshi.grpccommon.AuthResponse response = AuthResponseMapper.INSTANCE.toGrpcAuthResponse(authResponse);

            responseObserver.onNext(response); // Отправка ответа клиенту gRPC
            responseObserver.onCompleted(); // Завершение отправки ответа
        });
    }

    @Override
    public void getNewAccessToken(ru.mattakvshi.grpccommon.RefreshJwtRequest request, StreamObserver<ru.mattakvshi.grpccommon.AuthResponse> responseObserver) {
        RefreshJwtRequest refreshRequest = new RefreshJwtRequest(); // Создание нового объекта RefreshJwtRequest
        refreshRequest.setRefreshToken(request.getRefreshToken()); // Установка refreshToken из запроса

        // Вызов REST-сервиса и отправка ответа клиенту gRPC
        userAuthorizationClientService.getNewAccessTokenForUser(refreshRequest).subscribe(authResponse -> {

            // Подписка на результат вызова REST-сервиса
//            ru.mattakvshi.grpccommon.AuthResponse response = ru.mattakvshi.grpccommon.AuthResponse.newBuilder() // Создание нового объекта AuthResponse с помощью билдера
//                    .setAccessToken(authResponse.getAccessToken()) // Установка accessToken из ответа REST-сервиса
//                    .setRefreshToken(authResponse.getRefreshToken()) // Установка refreshToken из ответа REST-сервиса
//                    .setUuid(authResponse.getUuid().toString()) // Установка uuid из ответа REST-сервиса
//                    .build(); // Завершение создания объекта AuthResponse

            // Подписка на результат вызова REST-сервиса
            ru.mattakvshi.grpccommon.AuthResponse response = AuthResponseMapper.INSTANCE.toGrpcAuthResponse(authResponse);

            responseObserver.onNext(response); // Отправка ответа клиенту gRPC
            responseObserver.onCompleted(); // Завершение отправки ответа
        });
    }

    @Override
    public void getNewRefreshToken(ru.mattakvshi.grpccommon.RefreshJwtRequest request, StreamObserver<ru.mattakvshi.grpccommon.AuthResponse> responseObserver) {
        RefreshJwtRequest refreshRequest = new RefreshJwtRequest(); // Создание нового объекта RefreshJwtRequest
        refreshRequest.setRefreshToken(request.getRefreshToken()); // Установка refreshToken из запроса

        // Вызов REST-сервиса и отправка ответа клиенту gRPC
        userAuthorizationClientService.getNewRefreshTokenForUser(refreshRequest).subscribe(authResponse -> {

            // Подписка на результат вызова REST-сервиса
//            ru.mattakvshi.grpccommon.AuthResponse response = ru.mattakvshi.grpccommon.AuthResponse.newBuilder() // Создание нового объекта AuthResponse с помощью билдера
//                    .setAccessToken(authResponse.getAccessToken()) // Установка accessToken из ответа REST-сервиса
//                    .setRefreshToken(authResponse.getRefreshToken()) // Установка refreshToken из ответа REST-сервиса
//                    .setUuid(authResponse.getUuid().toString()) // Установка uuid из ответа REST-сервиса
//                    .build(); // Завершение создания объекта AuthResponse

            // Подписка на результат вызова REST-сервиса
            ru.mattakvshi.grpccommon.AuthResponse response = AuthResponseMapper.INSTANCE.toGrpcAuthResponse(authResponse);

            log.info(response + "");

            responseObserver.onNext(response); // Отправка ответа клиенту gRPC
            responseObserver.onCompleted(); // Завершение отправки ответа
        });
    }

    @Override
    public void getCurrentUser(ru.mattakvshi.grpccommon.UserRequest request, StreamObserver<ru.mattakvshi.grpccommon.UserResponse> responseObserver) {
        // Вызов REST-сервиса и отправка ответа клиенту gRPC
        var user = userAuthorizationClientService.getCurrentUser(request.getAccessToken());
        UserResponse userResponse = new UserResponse();
        userResponse.setPrincipal(user);

            // Подписка на результат вызова REST-сервиса
//            ru.mattakvshi.grpccommon.UserResponse response = ru.mattakvshi.grpccommon.UserResponse.newBuilder() // Создание нового объекта UserResponse с помощью билдера
//                    .setPrincipal(user.toString()) // Установка principal из ответа REST-сервиса
//                    .build(); // Завершение создания объекта UserResponse

            // Подписка на результат вызова REST-сервиса
            ru.mattakvshi.grpccommon.UserResponse response = UserResponseMapper.INSTANCE.toGrpcUserResponse(userResponse);

            responseObserver.onNext(response); // Отправка ответа клиенту gRPC
            responseObserver.onCompleted(); // Завершение отправки ответа

    }
}
