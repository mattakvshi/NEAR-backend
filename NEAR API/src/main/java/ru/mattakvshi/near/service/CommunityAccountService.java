package ru.mattakvshi.near.service;

import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContext;
import ru.mattakvshi.near.dto.auth.AuthRequests;
import ru.mattakvshi.near.dto.auth.AuthResponse;
import ru.mattakvshi.near.dto.community.CommunityDTOForCommunity;
import ru.mattakvshi.near.entity.auth.CommunityAccount;

public interface CommunityAccountService {
    @Transactional
    AuthResponse login(AuthRequests authRequests) throws AuthException;

    AuthResponse getAccessToken(String refreshToken) throws AuthException;

    AuthResponse refresh(String refreshToken) throws AuthException;

    @Transactional
    CommunityAccount saveCommunity(CommunityAccount communityAccount);

    CommunityAccount findByEmail(String email);

    CommunityAccount findByEmailAndPassword(String email, String password);

    @Cacheable(value = "getCommunityByContext", key = "#context")
    CommunityDTOForCommunity getCommunityByContext(SecurityContext context);
}
