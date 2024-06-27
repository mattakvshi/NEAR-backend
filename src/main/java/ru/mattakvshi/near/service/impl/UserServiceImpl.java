package ru.mattakvshi.near.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dao.UserDAO;
import ru.mattakvshi.near.entity.User;
import ru.mattakvshi.near.service.UserService;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UUID saveUser(User user) {
        return userDAO.saveUser(user);
    }
}
