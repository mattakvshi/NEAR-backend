package ru.mattakvshi.TelegramWorker.service.impl;

import ru.mattakvshi.TelegramWorker.service.GRPCClientService;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.mattakvshi.grpccommon.UserAuthorizationServiceGrpc;


@Service
public class GRPCClientServiceImpl implements GRPCClientService {

    @GrpcClient("userAuthorizationService")
    private UserAuthorizationServiceGrpc.UserAuthorizationServiceBlockingStub blockingStub;

    @Override
    public ru.mattakvshi.grpccommon.AuthResponse authUser(String email, String password) {
        ru.mattakvshi.grpccommon.AuthRequest request = ru.mattakvshi.grpccommon.AuthRequest.newBuilder()
                .setEmail(email)
                .setPassword(password)
                .build();
        return blockingStub.authUser(request);
    }

    @Override
    public ru.mattakvshi.grpccommon.AuthResponse getNewAccessToken(String refreshToken) {
        ru.mattakvshi.grpccommon.RefreshJwtRequest request = ru.mattakvshi.grpccommon.RefreshJwtRequest.newBuilder()
                .setRefreshToken(refreshToken)
                .build();
        return blockingStub.getNewAccessToken(request);
    }

    @Override
    public ru.mattakvshi.grpccommon.AuthResponse getNewRefreshToken(String refreshToken) {
        ru.mattakvshi.grpccommon.RefreshJwtRequest request = ru.mattakvshi.grpccommon.RefreshJwtRequest.newBuilder()
                .setRefreshToken(refreshToken)
                .build();
        return blockingStub.getNewRefreshToken(request);
    }

    @Override
    public ru.mattakvshi.grpccommon.UserResponse getCurrentUser(String accessToken) {
        ru.mattakvshi.grpccommon.UserRequest request = ru.mattakvshi.grpccommon.UserRequest.newBuilder()
                .setAccessToken(accessToken)
                .build();
        return blockingStub.getCurrentUser(request);
    }

}
