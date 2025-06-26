package ru.mattakvshi.near.dao.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.mattakvshi.near.dao.CommunityDAO;
import ru.mattakvshi.near.dao.repository.CommunityRepository;
import ru.mattakvshi.near.entity.base.Community;

import java.awt.print.Pageable;
import java.util.UUID;


@Component
public class CommunityDAOImpl implements CommunityDAO {

    @Autowired
    private CommunityRepository communityRepository;

    @Override
    @Transactional
    public UUID saveCommunity(Community community) {
        Community savedCommunity = communityRepository.save(community);
        return savedCommunity.getId();
    }

    @Override
    public Community findById(UUID id) {
        return communityRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Community> findAllByCommunityNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String nameQuery, String descriptionQuery, Pageable pageable
    ){
        return communityRepository.findAllByCommunityNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(nameQuery,descriptionQuery, pageable);
    };
}
