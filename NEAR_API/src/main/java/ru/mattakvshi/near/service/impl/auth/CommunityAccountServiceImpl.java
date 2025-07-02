package ru.mattakvshi.near.service.impl.auth;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.config.security.JWTProvider;
import ru.mattakvshi.near.dao.EmailChangeStorageCommunityDAO;
import ru.mattakvshi.near.dao.EmailVerificationTokenCommunityDAO;
import ru.mattakvshi.near.dao.repository.auth.CommunityAccountRepository;
import ru.mattakvshi.near.dao.repository.auth.CommunityRefreshRepository;
import ru.mattakvshi.near.dto.auth.AuthRequests;
import ru.mattakvshi.near.dto.auth.AuthResponse;
import ru.mattakvshi.near.entity.auth.*;
import ru.mattakvshi.near.service.CommunityAccountService;
import ru.mattakvshi.near.service.CommunityService;
import ru.mattakvshi.near.service.MailSender;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class CommunityAccountServiceImpl implements CommunityAccountService {

    @Autowired
    private CommunityAccountRepository communityAccountRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder; //Позволяет шифровать пороли и записывать в базу в зашифрованном виде

    @Autowired
    @Lazy
    private JWTProvider jwtProvider;

    @Autowired
    private CommunityRefreshRepository communityRefreshRepository;

    @Autowired
    private EmailVerificationTokenCommunityDAO emailVerificationTokenCommunityDAO;

    @Autowired
    private EmailChangeStorageCommunityDAO emailChangeStorageCommunityDAO;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CommunityService communityService;


    @Override
    @Transactional
    public AuthResponse login(@NonNull AuthRequests authRequests) throws AuthException {
        CommunityAccount communityAccount = findByEmailAndPassword(authRequests.getEmail(), authRequests.getPassword());
        if (communityAccount != null) {
            final String accessToken = jwtProvider.generateAccessToken(communityAccount);
            final String refreshToken = jwtProvider.generateRefreshToken(communityAccount);
            communityRefreshRepository.save(new CommunityRefreshStorage(communityAccount.getEmail(), refreshToken));
            return new AuthResponse(accessToken, refreshToken, communityAccount.getId());
        } else {
            throw new AuthException("Сообщество не найдено");
        }
    }


    //Отправляем ссылку подтверждения на почту пользователю (формируем токен)
    @Transactional
    @Override
    public void sendVerificationEmail(CommunityAccount communityAccount) {
        UUID token = UUID.randomUUID();

        // Удаляем все существующие токены для этого пользователя
        List<EmailVerificationTokenCommunity> existingTokens = emailVerificationTokenCommunityDAO.findByCommunityAccountId(communityAccount.getId());
        if (!existingTokens.isEmpty()) {
            emailVerificationTokenCommunityDAO.deleteAllByTokens(existingTokens);
        }

        // Создаем новый токен
        EmailVerificationTokenCommunity verificationToken = new EmailVerificationTokenCommunity(
                token,
                communityAccount.getId(),
                LocalDateTime.now(ZoneId.of("UTC")).plusHours(24)
        );

        emailVerificationTokenCommunityDAO.save(verificationToken);

        String verificationLink = "http://31.129.107.60:8080/NEAR/verify-email-community?token=" + token;
        mailSender.send(communityAccount.getEmail(), "Подтверждение почты", verificationLink);
    }

    @Transactional
    @Override
    public boolean verifyEmail(UUID token) {
        log.info("Поиск токена: {}", token);
        EmailVerificationTokenCommunity verificationToken = emailVerificationTokenCommunityDAO.findById(token);

        if (verificationToken == null) {
            log.warn("Токен не найден");
            return false;
        }

        // Проверяем срок действия
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now(ZoneId.of("UTC")))) {
            log.warn("Токен истек: {}", verificationToken.getExpiryDate());
            emailVerificationTokenCommunityDAO.delete(verificationToken);
            return false;
        }

        Optional<CommunityAccount> communityAccountOpt = communityAccountRepository.findById(
                verificationToken.getCommunityAccountId()
        );

        if (communityAccountOpt.isEmpty()) {
            log.warn("Аккаунт не найден для токена: {}", token);
            emailVerificationTokenCommunityDAO.delete(verificationToken);
            return false;
        }

        CommunityAccount account = communityAccountOpt.get();
        account.setEmailVerified(true);
        communityAccountRepository.save(account);

        // Удаляем использованный токен
        emailVerificationTokenCommunityDAO.delete(verificationToken);

        log.info("Email успешно подтверждён для пользователя: {}", account.getEmail());
        return true;
    }

    @Override
    public void requestEmailChange(UUID communityId, String newEmail) {
        CommunityAccount communityAccount = communityAccountRepository.findById(communityId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        createEmailChangeRequest(communityAccount, newEmail);
    }

    @Transactional
    @Override
    public void createEmailChangeRequest(CommunityAccount communityAccount, String newEmail) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(24);
        EmailChangeStorageCommunity request = new EmailChangeStorageCommunity(
                UUID.fromString(token),
                communityAccount.getId(),
                newEmail,
                expiryDate
        );
        emailChangeStorageCommunityDAO.save(request);
        // Отправляем ссылку для подтверждения
        String verificationLink = "http://31.129.107.60:8080/NEAR/confirm-email-change-community?token=" + token;
        mailSender.send(communityAccount.getEmail(), "Подтверждение изменения почты", verificationLink);
    }

    @Transactional
    @Override
    public boolean verifyEmailChange(UUID token) {
        EmailChangeStorageCommunity request = emailChangeStorageCommunityDAO.findById(token);
        if (request == null || request.getExpiryDate().isBefore(LocalDateTime.now())) {
            log.info("Error..." + request);
            return false;
        }

        // Обновляем email в UserAccount и User
        CommunityAccount communityAccount = findById(request.getCommunityAccountId());
        communityAccount.setEmail(request.getNewEmail());
        saveCommunityWithoutEncrypting(communityAccount); // Местный метод без повторного шифрования пароля

//        //Обновляем email в User
//        Community community = communityAccount.getCommunity();
//        community.setEmail(request.getNewEmail());
//        communityService.saveCommunity(community);

        // Удаляем использованный токен
        emailChangeStorageCommunityDAO.delete(request);
        return true;
    }




    @Override
    public AuthResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            var email = jwtProvider.getLoginFromRefreshToken(refreshToken);
            final String saveRefreshToken = communityRefreshRepository.findRefreshTokenByEmail(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final CommunityAccount communityAccount = findByEmail(email);
                if (communityAccount != null) {
                    final String accessToken = jwtProvider.generateAccessToken(communityAccount);
                    return new AuthResponse(accessToken, null, null);
                } else {
                    throw new AuthException("Сообщество не найдено");
                }
            }
        }
        return new AuthResponse(null, null, null);
    }

    @Override
    public AuthResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            var email = jwtProvider.getLoginFromRefreshToken(refreshToken);
            final String saveRefreshToken = communityRefreshRepository.findRefreshTokenByEmail(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final CommunityAccount communityAccount = findByEmail(email);

                if (communityAccount != null) {
                    final String accessToken = jwtProvider.generateAccessToken(communityAccount);
                    final String newRefreshToken = jwtProvider.generateRefreshToken(communityAccount);
                    communityRefreshRepository.save(new CommunityRefreshStorage(communityAccount.getEmail(), newRefreshToken));
                    return new AuthResponse(accessToken, newRefreshToken, null);
                } else {
                    throw new AuthException("Сообщество не найдено");
                }
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    @Override
    public UUID getCurrentCommunityAccountUUID() throws AuthException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CommunityAccount) {
            return ((CommunityAccount) authentication.getPrincipal()).getId();
        }
        return null;
    }

    @Override
    public UUID getCurrentCommunityUUID() throws AuthException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CommunityAccount) {
            return ((CommunityAccount) authentication.getPrincipal()).getCommunity().getId();
        }
        return null;
    }

    @Transactional
    @Override
    public CommunityAccount saveCommunity(CommunityAccount communityAccount) {;
        communityAccount.setPassword(passwordEncoder.encode(communityAccount.getPassword()));
        return communityAccountRepository.save(communityAccount);
    }

    @Override
    public CommunityAccount saveCommunityWithoutEncrypting(CommunityAccount communityAccount) {
        communityAccount.setPassword(communityAccount.getPassword());
        var communityAccountResponse = communityAccountRepository.save(communityAccount);
        entityManager.flush();
        return communityAccountResponse;
    }

    @Override
    public CommunityAccount findByEmail(String email) {
        return communityAccountRepository.findByEmail(email);
    }

    @Override
    public CommunityAccount findByEmailAndPassword(String email, String password) {
        CommunityAccount communityAccount = findByEmail(email);
        if (communityAccount != null) {
            if (passwordEncoder.matches(password, communityAccount.getPassword())) {
                return communityAccount;
            }
        }
        return null;
    }

    @Override
    public CommunityAccount findById(UUID id) {
        return communityAccountRepository.findById(id).orElse(null);
    }


//    @Override
//    //@Cacheable(value = "getCommunityByContext", key = "#context")
//    public CommunityDTOForCommunity getCommunityByContext(SecurityContext context){
//        CommunityAccount communityAccount = (CommunityAccount) context.getAuthentication().getPrincipal();
//        return (CommunityDTOForCommunity) communityAccount.getPrincipal();
//    }
}
