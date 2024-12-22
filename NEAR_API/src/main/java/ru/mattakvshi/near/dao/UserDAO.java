package ru.mattakvshi.near.dao;


import jakarta.transaction.Transactional;
import org.reactivestreams.Publisher;
import ru.mattakvshi.near.entity.base.User;

import java.util.List;
import java.util.UUID;

public interface UserDAO {
    @Transactional
    UUID saveUser(User user);

    User findById(UUID id);

    List<User> findAllById(List<UUID> ids);

}
