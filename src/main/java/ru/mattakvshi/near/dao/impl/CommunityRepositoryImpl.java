package ru.mattakvshi.near.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mattakvshi.near.dao.CommunityDAO;
import ru.mattakvshi.near.dao.repository.CommunityRepository;


@Component
public class CommunityRepositoryImpl implements CommunityDAO {

    @Autowired
    private CommunityRepository communityRepository;
}
