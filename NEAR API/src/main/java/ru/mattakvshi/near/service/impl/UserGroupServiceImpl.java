package ru.mattakvshi.near.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dao.GroupDAO;
import ru.mattakvshi.near.dto.actions.group.GroupFindRequest;
import ru.mattakvshi.near.dto.actions.group.GroupRequest;
import ru.mattakvshi.near.entity.Group;
import ru.mattakvshi.near.entity.base.User;
import ru.mattakvshi.near.service.UserGroupService;

@Service
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    private GroupDAO groupDAO;

    @Override
    @Transactional
    public void saveNewGroup(User user, GroupRequest groupRequest) {
        Group group = new Group();
        group.setGroupName(groupRequest.getGroupName());
        group.setOwner(user);
        group.setMembers(groupRequest.getMembers());
        groupDAO.save(group);
    }

    @Override
    @Transactional
    public void updateGroup(User user, GroupFindRequest groupFindRequest) {
        Group group = groupDAO.findById(groupFindRequest.getId());

        if (group == null) {
            throw new EntityNotFoundException("Group not found");
        } else if (!group.getOwner().equals(user)) {
            throw new EntityNotFoundException("This group is not in your ownership");
        } else {
            group.setGroupName(groupFindRequest.getGroupName());
            group.setMembers(groupFindRequest.getMembers());
            groupDAO.save(group);
        }
    }

    @Override
    @Transactional
    public void deleteGroup(User user, GroupFindRequest groupFindRequest) {
        Group group = groupDAO.findById(groupFindRequest.getId());

        if (group == null) {
            throw new EntityNotFoundException("Group not found");
        } else if (group.getOwner().equals(user)) {
            throw new EntityNotFoundException("This group is not in your ownership");
        } else {
            groupDAO.delete(group);
        }
    }
}
