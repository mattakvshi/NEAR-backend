package ru.mattakvshi.near.dao.repository.auth;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mattakvshi.near.entity.auth.CommunityRefreshStorage;

@Repository
public interface CommunityRefreshRepository extends CrudRepository<CommunityRefreshStorage, String> {

    @Query("SELECT rf.refreshToken FROM CommunityRefreshStorage rf WHERE rf.email = :email")
    String findRefreshTokenByEmail(String email);
}
