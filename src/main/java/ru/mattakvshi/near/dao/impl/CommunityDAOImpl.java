package ru.mattakvshi.near.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mattakvshi.near.dao.CommunityDAO;
import ru.mattakvshi.near.dao.repository.CommunityRepository;
import ru.mattakvshi.near.entity.Community;
import ru.mattakvshi.near.entity.User;

import java.util.UUID;


@Component
public class CommunityDAOImpl implements CommunityDAO {

    @Autowired
    private CommunityRepository communityRepository;

    @Override
    public UUID saveCommunity(Community community) {
        Community savedCommunity = communityRepository.save(community);
        return savedCommunity.getId();
    }

    @Override
    public Community findById(UUID id) {
        return communityRepository.findById(id).orElse(null);
    }
}
