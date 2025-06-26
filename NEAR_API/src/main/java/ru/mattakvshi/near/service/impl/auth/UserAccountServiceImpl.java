package ru.mattakvshi.near.service.impl.auth;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dao.EmailChangeStorageDAO;
import ru.mattakvshi.near.dao.EmailVerificationTokenDAO;
import ru.mattakvshi.near.dao.repository.auth.UserRefreshRepository;
import ru.mattakvshi.near.dao.repository.auth.UserAccountRepository;
import ru.mattakvshi.near.dto.auth.AuthRequests;
import ru.mattakvshi.near.dto.auth.AuthResponse;
import ru.mattakvshi.near.entity.auth.EmailChangeStorage;
import ru.mattakvshi.near.entity.auth.EmailVerificationToken;
import ru.mattakvshi.near.entity.auth.UserRefreshStorage;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.entity.base.User;
import ru.mattakvshi.near.service.MailSender;
import ru.mattakvshi.near.service.UserAccountService;
import ru.mattakvshi.near.config.security.JWTProvider;
import ru.mattakvshi.near.service.UserService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
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
    private UserRefreshRepository userRefreshRepository;

    @Autowired
    private EmailVerificationTokenDAO emailVerificationTokenDAO;

    @Autowired
    private EmailChangeStorageDAO emailChangeStorageDAO;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public AuthResponse login(@NonNull AuthRequests authRequests) throws AuthException {
        UserAccount userAccount = findByEmailAndPassword(authRequests.getEmail(), authRequests.getPassword());
        if (userAccount != null) {

            if (!userAccount.isEmailVerified()) {
                sendVerificationEmail(userAccount); // Повторная отправка письма
                throw new DisabledException("Email не подтвержден. Письмо отправлено повторно.");
            }

            final String accessToken = jwtProvider.generateAccessToken(userAccount);
            final String refreshToken = jwtProvider.generateRefreshToken(userAccount);
            userRefreshRepository.save(new UserRefreshStorage(userAccount.getEmail(), refreshToken));
            return new AuthResponse(accessToken, refreshToken, userAccount.getId());
        } else {
            throw new AuthException("Пользователь не найден");
        }
    }

    //Отправляем ссылку подтверждения на почту пользователю (формируем токен)
    @Override
    @Transactional
    public void sendVerificationEmail(UserAccount userAccount) {
        UUID token = UUID.randomUUID();

        // Удаляем все существующие токены для этого пользователя
        List<EmailVerificationToken> existingTokens = emailVerificationTokenDAO.findByUserAccountId(userAccount.getId());
        if (!existingTokens.isEmpty()) {
            emailVerificationTokenDAO.deleteAllByTokens(existingTokens);
        }

        // Создаем новый токен
        EmailVerificationToken verificationToken = new EmailVerificationToken(
                token,
                userAccount.getId(),
                LocalDateTime.now(ZoneId.of("UTC")).plusHours(24)
        );

        emailVerificationTokenDAO.save(verificationToken);

        String verificationLink = "http://localhost:8080/NEAR/verify-email?token=" + token;
        mailSender.send(userAccount.getEmail(), "Подтверждение почты", verificationLink);
    }

    @Override
    @Transactional
    public boolean verifyEmail(UUID token) {
        log.info("Поиск токена: {}", token);
        EmailVerificationToken verificationToken = emailVerificationTokenDAO.findById(token);

        if (verificationToken == null) {
            log.warn("Токен не найден");
            return false;
        }

        // Проверяем срок действия
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now(ZoneId.of("UTC")))) {
            log.warn("Токен истек: {}", verificationToken.getExpiryDate());
            emailVerificationTokenDAO.delete(verificationToken);
            return false;
        }

        Optional<UserAccount> userAccountOpt = userAccountRepository.findById(
                verificationToken.getUserAccountId()
        );

        if (userAccountOpt.isEmpty()) {
            log.warn("Аккаунт не найден для токена: {}", token);
            emailVerificationTokenDAO.delete(verificationToken);
            return false;
        }

        UserAccount account = userAccountOpt.get();
        account.setEmailVerified(true);
        userAccountRepository.save(account);

        // Удаляем использованный токен
        emailVerificationTokenDAO.delete(verificationToken);

        log.info("Email успешно подтверждён для пользователя: {}", account.getEmail());
        return true;
    }

    @Override
    public void requestEmailChange(UUID userId, String newEmail) {
        UserAccount userAccount = userAccountRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        createEmailChangeRequest(userAccount, newEmail);
    }

    @Override
    @Transactional
    public void createEmailChangeRequest(UserAccount userAccount, String newEmail) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(24);
        EmailChangeStorage request = new EmailChangeStorage(
                UUID.fromString(token),
                userAccount.getId(),
                newEmail,
                expiryDate
        );
        emailChangeStorageDAO.save(request);
        // Отправляем ссылку для подтверждения
        String verificationLink = "http://localhost:8080/NEAR/confirm-email-change?token=" + token;
        mailSender.send(userAccount.getEmail(), "Подтверждение изменения почты", verificationLink);
    }

    @Transactional
    @Override
    public boolean verifyEmailChange(UUID token) {
        EmailChangeStorage request = emailChangeStorageDAO.findById(token);
        if (request == null || request.getExpiryDate().isBefore(LocalDateTime.now())) {
            log.info("Error..." + request);
            return false;
        }

        // Обновляем email в UserAccount и User
        UserAccount userAccount = findById(request.getUserAccountId());
        userAccount.setEmail(request.getNewEmail());
        saveUserWithoutEncrypting(userAccount); // Местный метод без повторного шифрования пароля

        //Обновляем email в User
        User user = userAccount.getUser();
        user.setEmail(request.getNewEmail());
        userService.saveUser(user);

        // Удаляем использованный токен
        emailChangeStorageDAO.delete(request);
        return true;
    }

    @Override
    public AuthResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            var email = jwtProvider.getLoginFromRefreshToken(refreshToken);
            final String saveRefreshToken = userRefreshRepository.findRefreshTokenByEmail(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserAccount userAccount = findByEmail(email);
                if (userAccount != null) {
                    final String accessToken = jwtProvider.generateAccessToken(userAccount);
                    return new AuthResponse(accessToken, null, null);
                } else {
                    throw new AuthException("Пользователь не найден");
                }
            }
        }
        return new AuthResponse(null, null, null);
    }

    @Override
    public AuthResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            var email = jwtProvider.getLoginFromRefreshToken(refreshToken);
            final String saveRefreshToken = userRefreshRepository.findRefreshTokenByEmail(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserAccount userAccount = findByEmail(email);

                if (userAccount != null) {
                    final String accessToken = jwtProvider.generateAccessToken(userAccount);
                    final String newRefreshToken = jwtProvider.generateRefreshToken(userAccount);
                    userRefreshRepository.save(new UserRefreshStorage(userAccount.getEmail(), newRefreshToken));
                    return new AuthResponse(accessToken, newRefreshToken, null);
                } else {
                     throw new AuthException("Пользователь не найден");
                }
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    @Override
    public UUID getCurrentUserAccountUUID() throws AuthException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserAccount) {
            return ((UserAccount) authentication.getPrincipal()).getId();
        }
        return null;
    }

    @Override
    public UUID getCurrentUserUUID() throws AuthException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserAccount) {
            return ((UserAccount) authentication.getPrincipal()).getUser().getId();
        }
        return null;
    }

    @Transactional
    @Override
    public UserAccount saveUser(UserAccount userAccount) {
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        var userAccountResponse = userAccountRepository.save(userAccount);
        entityManager.flush();
        return userAccountResponse;
    }


    @Override
    public UserAccount saveUserWithoutEncrypting(UserAccount userAccount) {
        userAccount.setPassword(userAccount.getPassword());
        var userAccountResponse = userAccountRepository.save(userAccount);
        entityManager.flush();
        return userAccountResponse;
    }

    @Override
    public UserAccount findByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }

    @Override
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
