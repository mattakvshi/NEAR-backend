package ru.mattakvshi.near.service;

import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dao.repository.auth.UserAccountRepository;
import ru.mattakvshi.near.dto.AuthRequests;
import ru.mattakvshi.near.dto.AuthResponse;
import ru.mattakvshi.near.entity.auth.UserAccount;

import java.util.UUID;

public interface UserAccountService {
    @Transactional
    AuthResponse login(AuthRequests authRequests) throws AuthException;

    AuthResponse getAccessToken(String refreshToken) throws AuthException;

    AuthResponse refresh(String refreshToken) throws AuthException;

    @Transactional
    UserAccount saveUser(UserAccount userAccount);

    UserAccount findByEmail(String email);

    UserAccount findByEmailAndPassword(String email, String password) throws AuthException;

    UserAccount findById(UUID id);
}
