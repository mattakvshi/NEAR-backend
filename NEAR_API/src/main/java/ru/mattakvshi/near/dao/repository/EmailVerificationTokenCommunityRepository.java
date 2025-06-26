package ru.mattakvshi.near.dao.repository;

import org.springframework.data.repository.CrudRepository;
import ru.mattakvshi.near.entity.auth.EmailVerificationToken;
import ru.mattakvshi.near.entity.auth.EmailVerificationTokenCommunity;

import java.util.List;
import java.util.UUID;

public interface EmailVerificationTokenCommunityRepository extends CrudRepository <EmailVerificationTokenCommunity, UUID> {
    List<EmailVerificationTokenCommunity> findAllByCommunityAccountId(UUID communityAccountId);
}
