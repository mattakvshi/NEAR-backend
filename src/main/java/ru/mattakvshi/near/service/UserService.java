package ru.mattakvshi.near.service;

import ru.mattakvshi.near.entity.User;

import java.util.UUID;

public interface UserService {
    UUID saveUser(User user);
}
