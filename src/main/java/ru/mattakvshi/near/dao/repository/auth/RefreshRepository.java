package ru.mattakvshi.near.dao.repository.auth;

import org.springframework.data.repository.CrudRepository;
import ru.mattakvshi.near.entity.auth.RefreshStorage;

public interface RefreshRepository extends CrudRepository<RefreshStorage, String> {
}
