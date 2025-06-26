package ru.mattakvshi.near.service;

import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContext;
import ru.mattakvshi.near.dto.auth.AuthRequests;
import ru.mattakvshi.near.dto.auth.AuthResponse;
import ru.mattakvshi.near.dto.community.CommunityDTOForCommunity;
import ru.mattakvshi.near.entity.auth.CommunityAccount;

import java.util.UUID;

public interface CommunityAccountService {
    @Transactional
    AuthResponse login(AuthRequests authRequests) throws AuthException;

    //Отправляем ссылку подтверждения на почту пользователю (формируем токен)
    @Transactional
    void sendVerificationEmail(CommunityAccount communityAccount);

    @Transactional
    boolean verifyEmail(UUID token);

    void requestEmailChange(UUID communityId, String newEmail);

    @Transactional
    void createEmailChangeRequest(CommunityAccount communityAccount, String newEmail);

    @Transactional
    boolean verifyEmailChange(UUID token);

    AuthResponse getAccessToken(String refreshToken) throws AuthException;

    AuthResponse refresh(String refreshToken) throws AuthException;

    UUID getCurrentCommunityAccountUUID() throws AuthException;

    UUID getCurrentCommunityUUID() throws AuthException;

    @Transactional
    CommunityAccount saveCommunity(CommunityAccount communityAccount);

    CommunityAccount saveCommunityWithoutEncrypting(CommunityAccount communityAccount);

    CommunityAccount findByEmail(String email);

    CommunityAccount findByEmailAndPassword(String email, String password);

    CommunityAccount findById(UUID id);
}
