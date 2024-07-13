package ru.mattakvshi.near.dao;


import ru.mattakvshi.near.entity.Group;

import java.util.UUID;

public interface GroupDAO {
    void save(Group group);

    void delete(Group group);

    Group findById(UUID id);
}
