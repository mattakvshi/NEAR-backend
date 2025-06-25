package ru.mattakvshi.near.dao;

import jakarta.transaction.Transactional;
import ru.mattakvshi.near.entity.auth.EmailVerificationToken;

import java.util.List;
import java.util.UUID;

public interface EmailVerificationTokenDAO {

    void save(EmailVerificationToken emailVerificationToken);

    void delete(EmailVerificationToken emailVerificationToken);

    void deleteAllByTokens(List<EmailVerificationToken> tokens);

    List<EmailVerificationToken> findByUserAccountId(UUID userAccountId);

    EmailVerificationToken findById(UUID token);

}
