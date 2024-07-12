package ru.mattakvshi.TelegramWorker.service;


public interface GRPCClientService {

    ru.mattakvshi.grpccommon.AuthResponse authUser(String email, String password);

    ru.mattakvshi.grpccommon.AuthResponse getNewAccessToken(String refreshToken);

    ru.mattakvshi.grpccommon.AuthResponse getNewRefreshToken(String refreshToken);

    ru.mattakvshi.grpccommon.UserResponse getCurrentUser();
}
