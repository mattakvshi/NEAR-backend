package ru.mattakvshi.near.service.impl.actions;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dao.CommunityDAO;
import ru.mattakvshi.near.dao.UserDAO;
import ru.mattakvshi.near.entity.base.Community;
import ru.mattakvshi.near.entity.base.User;
import ru.mattakvshi.near.service.SubsService;

import java.util.UUID;

@Service
public class SubsServiceImpl implements SubsService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CommunityDAO communityDAO;

    @Override
    @Transactional
    public void subscribeUserToCommunity(UUID userId, UUID communityId) {
        User user = userDAO.findById(userId);
        Community community = communityDAO.findById(communityId);

        if(user != null && community != null) {
            // Проверяем, не подписан ли уже пользователь на сообщество
            if(!user.getSubscriptions().contains(community)) {
                user.getSubscriptions().add(community);
                community.getSubscribers().add(user);

                userDAO.saveUser(user);
                communityDAO.saveCommunity(community);
            } else {
                // Обработка ситуации, когда пользователь уже подписан на сообщество
                throw new RuntimeException("User is already subscribed to the community");
            }

        } else if (user == null) {
            throw new RuntimeException("User not found");
        } else {
            throw new RuntimeException("Community not found");
        }
    }

    @Override
    @Transactional
    public void cancelSubscriptionUserToCommunity(UUID userId, UUID communityId) {
        User user = userDAO.findById(userId);
        Community community = communityDAO.findById(communityId);

        if(user != null && community != null) {

            if(user.getSubscriptions().contains(community)) {

                user.getSubscriptions().remove(community);
                community.getSubscribers().remove(user);

                userDAO.saveUser(user);
                communityDAO.saveCommunity(community);

            } else {
                throw new RuntimeException("User is not subscribed to the community");
            }

        } else if (user == null) {
            throw new RuntimeException("User not found");
        } else {
            throw new RuntimeException("Community not found");
        }
    }

}
