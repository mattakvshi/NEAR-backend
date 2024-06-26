package ru.mattakvshi.near.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dao.UserDAO;

@Service
public class UserServiceImpl {

    @Autowired
    private UserDAO userDAO;
}
