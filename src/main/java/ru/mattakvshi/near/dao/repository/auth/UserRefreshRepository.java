package ru.mattakvshi.near.dao.repository.auth;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mattakvshi.near.entity.auth.UserRefreshStorage;

@Repository
public interface UserRefreshRepository extends CrudRepository<UserRefreshStorage, String> {

    @Query("SELECT rf.refreshToken FROM UserRefreshStorage rf WHERE rf.email = :email")
    String findRefreshTokenByEmail(String email);
}
