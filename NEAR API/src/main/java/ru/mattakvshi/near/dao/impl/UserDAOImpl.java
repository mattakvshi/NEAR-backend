package ru.mattakvshi.near.dao.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mattakvshi.near.dao.UserDAO;
import ru.mattakvshi.near.dao.repository.UserRepository;
import ru.mattakvshi.near.entity.base.User;

import java.util.List;
import java.util.UUID;


@Component
public class UserDAOImpl implements UserDAO {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UUID saveUser(User user) {
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAllById(List<UUID> ids) {
        return (List<User>) userRepository.findAllById(ids);
    }
}
