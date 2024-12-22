package ru.mattakvshi.near.service.impl.actions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dao.GroupDAO;
import ru.mattakvshi.near.dto.actions.group.GroupFindRequest;
import ru.mattakvshi.near.dto.actions.group.GroupRequest;
import ru.mattakvshi.near.entity.Group;
import ru.mattakvshi.near.entity.base.User;
import ru.mattakvshi.near.service.UserGroupService;
import ru.mattakvshi.near.service.UserService;

import java.util.stream.Collectors;

@Slf4j
@Service
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void saveNewGroup(User user, GroupRequest groupRequest) {
        Group group = new Group();
        group.setGroupName(groupRequest.getGroupName());
        group.setOwner(user);
        group.setMembers(
                groupRequest.getMembers()
                .stream()
                .map(userService::getUser)
                .collect(Collectors.toList())
        );

        boolean allMembersAreFriends = group.getMembers().stream()
                .allMatch(member -> user.getFriends().stream()
                        .anyMatch(friend -> friend.getId().equals(member.getId())));

        if (allMembersAreFriends) {
            groupDAO.save(group);
        } else {
            throw new EntityNotFoundException("You can create a group only from the users that you have as friends");
        }

    }

    @Override
    @Transactional
    public void updateGroup(User user, GroupFindRequest groupFindRequest) {
        Group group = groupDAO.findById(groupFindRequest.getId());

        if (group == null) {
            throw new EntityNotFoundException("Group not found");
        } else if (!group.getOwner().getId().equals(user.getId())) {
            throw new EntityNotFoundException("This group is not in your ownership");
        } else {
            group.setGroupName(groupFindRequest.getGroupName());
            group.setMembers(
                    groupFindRequest.getMembers()
                            .stream()
                            .map(userService::getUser)
                            .collect(Collectors.toList())
            );
            groupDAO.save(group);
        }
    }

    @Override
    @Transactional
    public void deleteGroup(User user, GroupFindRequest groupFindRequest) {
        Group group = groupDAO.findById(groupFindRequest.getId());

        if (group == null) {
            throw new EntityNotFoundException("Group not found");
        } else if (!group.getOwner().getId().equals(user.getId())) {
            throw new EntityNotFoundException("This group is not in your ownership");
        } else {
            groupDAO.delete(group);
        }
    }
}
