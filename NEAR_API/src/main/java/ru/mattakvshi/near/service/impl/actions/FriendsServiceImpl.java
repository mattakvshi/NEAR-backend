package ru.mattakvshi.near.service.impl.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dao.UserDAO;
import ru.mattakvshi.near.entity.base.User;
import ru.mattakvshi.near.service.FriendsService;

import java.util.UUID;

@Service
public class FriendsServiceImpl implements FriendsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public void friendRequest(UUID userId, UUID friendId) {
        User user = userDAO.findById(userId);
        User friend = userDAO.findById(friendId);

        if(user != null && friend != null) {

            if(!user.getSentRequests().contains(friend)){

                user.getSentRequests().add(friend); // Нам добавляем в отправленные запросы
                friend.getReceivedRequests().add(user); //Ему добавляем в полученные запросы

                userDAO.saveUser(user);
                userDAO.saveUser(friend);

            } else {
                throw new RuntimeException("A friend request has already been sent to this user");
            }

        } else if (user == null) {
            throw new RuntimeException("User not found");
        } else {
            throw new RuntimeException("Friend not found");
        }
    }

    @Override
    public void addNewFriend(UUID userId, UUID friendId) {
        User user = userDAO.findById(userId);
        User friend = userDAO.findById(friendId);

        if(user != null && friend != null) {

            if(!user.getFriends().contains(friend) && user.getReceivedRequests().contains(friend)){

                user.getFriends().add(friend);
                friend.getFriends().add(user);

                user.getReceivedRequests().remove(friend); // Если мы кого-то добавили, удаляем его из полученных запросов у нас
                friend.getSentRequests().remove(user); // У него удаляем из отправленных

                userDAO.saveUser(user);
                userDAO.saveUser(friend);

            } else {
                throw new RuntimeException("This user is already your friend or don't send friend request");
            }

        } else if (user == null) {
            throw new RuntimeException("User not found");
        } else {
            throw new RuntimeException("Friend not found");
        }
    }

    @Override
    public void rejectFriendsRequest(UUID userId, UUID friendId) {

        User user = userDAO.findById(userId);
        User friend = userDAO.findById(friendId);

        if(user != null && friend != null) {

            if(user.getReceivedRequests().contains(friend)){

                user.getReceivedRequests().remove(friend); // При отклонении заявки удаляем у нас из полученных
                friend.getSentRequests().remove(user); // У него из отправленных

                userDAO.saveUser(user);
                userDAO.saveUser(friend);

            } else {
                throw new RuntimeException("The friend request has already been rejected");
            }

        } else if (user == null) {
            throw new RuntimeException("User not found");
        } else {
            throw new RuntimeException("Friend not found");
        }
    }

    @Override
    public void deleteFriend(UUID userId, UUID friendId) {

        User user = userDAO.findById(userId);
        User friend = userDAO.findById(friendId);

        if(user != null && friend != null) {

            if(user.getFriends().contains(friend)){

                user.getFriends().remove(friend);
                friend.getFriends().remove(user);

                userDAO.saveUser(user);
                userDAO.saveUser(friend);

            } else {
                throw new RuntimeException("This user is already not your friend");
            }

        } else if (user == null) {
            throw new RuntimeException("User not found");
        } else {
            throw new RuntimeException("Friend not found");
        }

    }
}
