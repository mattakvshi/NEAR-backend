package ru.mattakvshi.near.dao;


import jakarta.transaction.Transactional;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Page;
import ru.mattakvshi.near.entity.base.User;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface UserDAO {
    @Transactional
    UUID saveUser(User user);

    User findById(UUID id);

    List<User> findAllById(List<UUID> ids);

    Page<User> findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstNameQuery, String lastNameQuery, Pageable pageable
    );
}
