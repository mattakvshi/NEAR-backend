package ru.mattakvshi.near.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mattakvshi.near.dao.GroupDAO;
import ru.mattakvshi.near.dao.repository.GroupRepository;
import ru.mattakvshi.near.entity.Group;

import java.util.UUID;

@Component
public class GroupDAOImpl implements GroupDAO {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public void save(Group group) {
        groupRepository.save(group);
    }

    @Override
    public void delete(Group group) {
        groupRepository.delete(group);
    }

    @Override
    public Group findById(UUID id) {
        return groupRepository.findById(id).orElse(null);
    }
}
