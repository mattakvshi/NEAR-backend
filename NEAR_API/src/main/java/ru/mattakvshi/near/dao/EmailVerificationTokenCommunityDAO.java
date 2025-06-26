package ru.mattakvshi.near.dao;

import ru.mattakvshi.near.entity.auth.EmailVerificationToken;
import ru.mattakvshi.near.entity.auth.EmailVerificationTokenCommunity;

import java.util.List;
import java.util.UUID;

public interface EmailVerificationTokenCommunityDAO {

    void save(EmailVerificationTokenCommunity emailVerificationTokenCommunity);

    void delete(EmailVerificationTokenCommunity emailVerificationTokenCommunity);

    void deleteAllByTokens(List<EmailVerificationTokenCommunity> tokens);

    List<EmailVerificationTokenCommunity> findByCommunityAccountId(UUID communityAccountId);

    EmailVerificationTokenCommunity findById(UUID token);

}
