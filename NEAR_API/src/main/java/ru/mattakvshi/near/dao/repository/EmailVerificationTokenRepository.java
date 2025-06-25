package ru.mattakvshi.near.dao.repository;

import org.springframework.data.repository.CrudRepository;
import ru.mattakvshi.near.entity.auth.EmailVerificationToken;

import java.util.List;
import java.util.UUID;

public interface EmailVerificationTokenRepository extends CrudRepository <EmailVerificationToken, UUID> {
    List<EmailVerificationToken> findAllByUserAccountId(UUID userAccountId);
}
