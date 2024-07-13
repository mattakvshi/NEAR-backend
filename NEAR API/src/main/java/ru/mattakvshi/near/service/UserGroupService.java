package ru.mattakvshi.near.service;


import jakarta.transaction.Transactional;
import ru.mattakvshi.near.dto.actions.group.GroupFindRequest;
import ru.mattakvshi.near.dto.actions.group.GroupRequest;
import ru.mattakvshi.near.entity.base.User;

public interface UserGroupService {

    @Transactional
    void saveNewGroup(User user, GroupRequest groupRequest);

    @Transactional
    void updateGroup(User user, GroupFindRequest groupFindRequest);

    @Transactional
    void deleteGroup(User user, GroupFindRequest groupFindRequest);
}
