package ru.mattakvshi.near.dao.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mattakvshi.near.dao.EmailVerificationTokenDAO;
import ru.mattakvshi.near.dao.repository.EmailVerificationTokenRepository;
import ru.mattakvshi.near.entity.auth.EmailVerificationToken;

import java.util.List;
import java.util.UUID;

@Component
public class EmailVerificationTokenDAOImpl implements EmailVerificationTokenDAO {

    @Autowired
    private EmailVerificationTokenRepository emailVerificationTokenRepository;

    @Transactional
    @Override
    public void save(EmailVerificationToken token) {
        emailVerificationTokenRepository.save(token);
    }

    @Transactional
    @Override
    public void delete(EmailVerificationToken token) {
        emailVerificationTokenRepository.delete(token);
    }

    @Transactional
    @Override
    public void deleteAllByTokens(List<EmailVerificationToken> tokens) {
        emailVerificationTokenRepository.deleteAll(tokens);
    }

    @Override
    public List<EmailVerificationToken> findByUserAccountId(UUID userAccountId) {
        return emailVerificationTokenRepository.findAllByUserAccountId(userAccountId);

    }

    @Override
    public EmailVerificationToken findById(UUID token) {
        return emailVerificationTokenRepository.findById(token).orElse(null);
    }
}
