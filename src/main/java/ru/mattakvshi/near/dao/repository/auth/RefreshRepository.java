package ru.mattakvshi.near.dao.repository.auth;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.mattakvshi.near.entity.auth.RefreshStorage;

public interface RefreshRepository extends CrudRepository<RefreshStorage, String> {

    @Query("SELECT rf.refreshToken FROM RefreshStorage rf WHERE rf.email = :email")
    String findRefreshTokenByEmail(String email);
}
