package ru.mattakvshi.near.service;

import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContext;
import ru.mattakvshi.near.dto.auth.AuthRequests;
import ru.mattakvshi.near.dto.auth.AuthResponse;
import ru.mattakvshi.near.dto.user.UserDTOForUser;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.entity.base.User;

import java.util.UUID;

public interface UserAccountService {
    @Transactional
    AuthResponse login(AuthRequests authRequests) throws AuthException;

    void sendVerificationEmail(UserAccount userAccount);

    boolean verifyEmail(UUID token);

    void requestEmailChange(UUID userId, String newEmail);

    void createEmailChangeRequest(UserAccount userAccount, String newEmail);

    boolean verifyEmailChange(UUID token);

    AuthResponse getAccessToken(String refreshToken) throws AuthException;

    AuthResponse refresh(String refreshToken) throws AuthException;

    UUID getCurrentUserAccountUUID() throws AuthException;

    UUID getCurrentUserUUID() throws AuthException;

    UserAccount saveUser(UserAccount userAccount);

    UserAccount findByEmail(String email);

    UserAccount findByEmailAndPassword(String email, String password) throws AuthException;

    UserAccount findById(UUID id);
}
