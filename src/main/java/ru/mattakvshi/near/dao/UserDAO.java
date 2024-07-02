package ru.mattakvshi.near.dao;


import jakarta.transaction.Transactional;
import ru.mattakvshi.near.entity.User;

import java.util.UUID;

public interface UserDAO {
    @Transactional
    UUID saveUser(User user);

    User findById(UUID id);

}
