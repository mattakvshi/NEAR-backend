package ru.mattakvshi.near.dao.repository.auth;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.entity.base.User;

import java.util.UUID;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, UUID> {
   UserAccount findByEmail(String email);
}
