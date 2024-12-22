package ru.mattakvshi.near.dao.repository.auth;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mattakvshi.near.entity.Group;

import java.util.UUID;

@Repository
public interface GroupRepository extends CrudRepository<Group, UUID> {
}
