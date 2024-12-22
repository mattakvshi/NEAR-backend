package ru.mattakvshi.near.dao.repository.auth;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mattakvshi.near.entity.auth.CommunityAccount;
import ru.mattakvshi.near.entity.auth.UserAccount;

import java.util.UUID;

@Repository
public interface CommunityAccountRepository extends CrudRepository<CommunityAccount, UUID> {
    CommunityAccount findByEmail(String email);
}
