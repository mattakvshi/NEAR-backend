package ru.mattakvshi.near.dao.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mattakvshi.near.dao.EmailVerificationTokenCommunityDAO;
import ru.mattakvshi.near.dao.EmailVerificationTokenDAO;
import ru.mattakvshi.near.dao.repository.EmailVerificationTokenCommunityRepository;
import ru.mattakvshi.near.dao.repository.EmailVerificationTokenRepository;
import ru.mattakvshi.near.entity.auth.EmailVerificationToken;
import ru.mattakvshi.near.entity.auth.EmailVerificationTokenCommunity;

import java.util.List;
import java.util.UUID;

@Component
public class EmailVerificationTokenCommunityDAOImpl implements EmailVerificationTokenCommunityDAO {

    @Autowired
    private EmailVerificationTokenCommunityRepository emailVerificationTokenCommunityRepository;

    @Transactional
    @Override
    public void save(EmailVerificationTokenCommunity token) {
        emailVerificationTokenCommunityRepository.save(token);
    }

    @Transactional
    @Override
    public void delete(EmailVerificationTokenCommunity token) {
        emailVerificationTokenCommunityRepository.delete(token);
    }

    @Transactional
    @Override
    public void deleteAllByTokens(List<EmailVerificationTokenCommunity> tokens) {
        emailVerificationTokenCommunityRepository.deleteAll(tokens);
    }

    @Override
    public List<EmailVerificationTokenCommunity> findByCommunityAccountId(UUID communityAccountId) {
        return emailVerificationTokenCommunityRepository.findAllByCommunityAccountId(communityAccountId);

    }

    @Override
    public EmailVerificationTokenCommunity findById(UUID token) {
        return emailVerificationTokenCommunityRepository.findById(token).orElse(null);
    }
}
