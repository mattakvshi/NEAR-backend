package ru.mattakvshi.near.service.impl;

import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dao.repository.auth.RefreshRepository;
import ru.mattakvshi.near.dao.repository.auth.UserAccountRepository;
import ru.mattakvshi.near.dto.AuthRequests;
import ru.mattakvshi.near.dto.AuthResponse;
import ru.mattakvshi.near.entity.auth.RefreshStorage;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.service.UserAccountService;
import ru.mattakvshi.near.config.security.JWTProvider;

import java.util.UUID;


@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder; //Позволяет шифровать пороли и записывать в базу в зашифрованном виде

    @Autowired
    @Lazy
    private JWTProvider jwtProvider;

    @Autowired
    private RefreshRepository refreshRepository;

    @Override
    @Transactional
    public AuthResponse login(AuthRequests authRequests) throws AuthException {
        UserAccount userAccount = findByEmailAndPassword(authRequests.getEmail(), authRequests.getPassword());
        if (userAccount != null) {
            final String accessToken = jwtProvider.generateAccessToken(userAccount);
            final String refreshToken = jwtProvider.generateRefreshToken(userAccount);
            refreshRepository.save(new RefreshStorage(userAccount.getEmail(), refreshToken));
            return new AuthResponse(accessToken, refreshToken, userAccount.getId());
        } else {
            throw new AuthException("Пользователь не найден");
        }
    }

    public UserAccount saveUser(UserAccount userAccount) {

        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        return userAccountRepository.save(userAccount);
    }

    public UserAccount findByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }

    public UserAccount findByEmailAndPassword(String email, String password) throws AuthException {
        UserAccount userAccount = findByEmail(email);
        if (userAccount != null) {
            if (passwordEncoder.matches(password, userAccount.getPassword())) {
                return userAccount;
            } else {
                throw new AuthException("Неправильный пароль");
            }
        }
        return null;
    }

    @Override
    public UserAccount findById(UUID id) {
        return userAccountRepository.findById(id).orElse(null);
    }
}
