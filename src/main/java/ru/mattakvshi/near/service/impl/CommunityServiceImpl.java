package ru.mattakvshi.near.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dao.CommunityDAO;

@Service
public class CommunityServiceImpl {

    @Autowired
    private CommunityDAO communityDAO;
}
