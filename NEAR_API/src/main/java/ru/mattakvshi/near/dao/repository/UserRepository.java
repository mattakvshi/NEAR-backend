package ru.mattakvshi.near.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mattakvshi.near.entity.base.Community;
import ru.mattakvshi.near.entity.base.User;

import java.awt.print.Pageable;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    Page<User> findAllByUserNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String nameQuery, String descriptionQuery, Pageable pageable
    );
}
