package ru.mattakvshi.near.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mattakvshi.near.dao.UserDAO;
import ru.mattakvshi.near.dao.repository.CommunityRepository;
import ru.mattakvshi.near.dao.repository.UserRepository;


@Component
public class UserRepositoryImpl implements UserDAO {

    @Autowired
    private UserRepository userRepository;
}
