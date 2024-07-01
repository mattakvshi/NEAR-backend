package ru.mattakvshi.near.service;

import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import ru.mattakvshi.near.dto.AuthRequests;
import ru.mattakvshi.near.dto.AuthResponse;
import ru.mattakvshi.near.entity.auth.CommunityAccount;
import ru.mattakvshi.near.entity.auth.UserAccount;

public interface CommunityAccountService {
    @Transactional
    AuthResponse login(AuthRequests authRequests) throws AuthException;

    AuthResponse getAccessToken(String refreshToken) throws AuthException;

    AuthResponse refresh(String refreshToken) throws AuthException;

    @Transactional
    CommunityAccount saveCommunity(CommunityAccount communityAccount);

    CommunityAccount findByEmail(String email);

    CommunityAccount findByEmailAndPassword(String email, String password);
}
