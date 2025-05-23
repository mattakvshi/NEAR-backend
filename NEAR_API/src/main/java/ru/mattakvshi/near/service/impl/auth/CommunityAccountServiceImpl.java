package ru.mattakvshi.near.service.impl.auth;

import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.config.security.JWTProvider;
import ru.mattakvshi.near.dao.repository.auth.CommunityAccountRepository;
import ru.mattakvshi.near.dao.repository.auth.CommunityRefreshRepository;
import ru.mattakvshi.near.dto.auth.AuthRequests;
import ru.mattakvshi.near.dto.auth.AuthResponse;
import ru.mattakvshi.near.dto.community.CommunityDTOForCommunity;
import ru.mattakvshi.near.entity.auth.CommunityAccount;
import ru.mattakvshi.near.entity.auth.CommunityRefreshStorage;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.service.CommunityAccountService;

import java.util.UUID;

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
    public UUID getCurrentCommunityUUID() throws AuthException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CommunityAccount) {
            return ((CommunityAccount) authentication.getPrincipal()).getCommunity().getId();
        }
        return null;
    }

    public CommunityAccount saveCommunity(CommunityAccount communityAccount) {;
        communityAccount.setPassword(passwordEncoder.encode(communityAccount.getPassword()));
        return communityAccountRepository.save(communityAccount);
    }


    public CommunityAccount findByEmail(String email) {
        return communityAccountRepository.findByEmail(email);
    }


    public CommunityAccount findByEmailAndPassword(String email, String password) {
        CommunityAccount communityAccount = findByEmail(email);
        if (communityAccount != null) {
            if (passwordEncoder.matches(password, communityAccount.getPassword())) {
                return communityAccount;
            }
        }
        return null;
    }

//    @Override
//    //@Cacheable(value = "getCommunityByContext", key = "#context")
//    public CommunityDTOForCommunity getCommunityByContext(SecurityContext context){
//        CommunityAccount communityAccount = (CommunityAccount) context.getAuthentication().getPrincipal();
//        return (CommunityDTOForCommunity) communityAccount.getPrincipal();
//    }
}
