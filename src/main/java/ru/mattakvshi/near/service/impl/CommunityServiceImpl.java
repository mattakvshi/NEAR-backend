package ru.mattakvshi.near.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dao.CommunityDAO;
import ru.mattakvshi.near.entity.Community;
import ru.mattakvshi.near.service.CommunityService;

import java.util.UUID;

@Service
public class CommunityServiceImpl  implements CommunityService {

    @Autowired
    private CommunityDAO communityDAO;

    @Override
    public UUID saveCommunity(Community community) {
        return communityDAO.saveCommunity(community);
    }
}
