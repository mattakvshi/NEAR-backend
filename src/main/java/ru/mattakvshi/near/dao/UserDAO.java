package ru.mattakvshi.near.dao;


import ru.mattakvshi.near.entity.User;

import java.util.UUID;

public interface UserDAO {
    UUID saveUser(User user);
}
