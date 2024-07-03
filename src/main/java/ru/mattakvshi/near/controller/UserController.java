package ru.mattakvshi.near.controller;


import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mattakvshi.near.dto.actions.AddFriendsRequest;
import ru.mattakvshi.near.dto.actions.SubscribeRequest;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.service.UserService;

@RestController
@Log
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

//    @PostMapping("/user/subscribe")
//    public String subscribeUserToCommunity(@RequestBody SubscribeRequest subscribeRequest) {
//        //TODO: Подумать над тем чтобы не с фронта запрашивать userID, а брать просто id из текущего контекста по access токену (Спросить у Юры)
//        try {
//            userService.subscribeUserToCommunity(subscribeRequest.getUserId(), subscribeRequest.getCommunityId());
//            return "OK";
//        } catch (Exception e) {
//            log.info("Exception: " + e);
//        }
//       return "Not ok";
//    }
//
//    @PostMapping("/user/add/friend")
//    public String addNewFriend(@RequestBody AddFriendsRequest addFriendsRequest) {
//        //TODO: Подумать над тем чтобы не с фронта запрашивать userID, а брать просто id из текущего контекста по access токену (Спросить у Юры)
//        try{
//            userService.addNewFriend(addFriendsRequest.getUserId(), addFriendsRequest.getFriendId());
//            return "OK";
//        } catch (Exception e) {
//            log.info("Exception: " + e);
//        }
//        return "Not ok";
//    }

    @PostMapping("/user/subscribe")
    public String subscribeUserToCommunity(@RequestBody SubscribeRequest subscribeRequest) {
        try {

            UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userService.subscribeUserToCommunity(
                    userAccount.getUser().getId(),
                    subscribeRequest.getCommunityId()
            );
            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
        return "Not ok";
    }

    @PostMapping("/user/cancel/subscription")
    public String cancelSubscriptionUserToCommunity(@RequestBody SubscribeRequest subscribeRequest) {
        try {

            UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userService.cancelSubscriptionUserToCommunity(
                    userAccount.getUser().getId(),
                    subscribeRequest.getCommunityId()
            );
            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
        return "Not ok";
    }

    @PostMapping("/user/request/friend")
    public String friendRequest(@RequestBody AddFriendsRequest addFriendsRequest) {
        try{
            UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userService.friendRequest(
                    userAccount.getUser().getId(),
                    addFriendsRequest.getFriendId()
            );
            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
        return "Not ok";
    }

    @PostMapping("/user/add/friend")
    public String addNewFriend(@RequestBody AddFriendsRequest addFriendsRequest) {
        try{
            UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userService.addNewFriend(
                    userAccount.getUser().getId(),
                    addFriendsRequest.getFriendId()
            );
            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
        return "Not ok";
    }

    @PostMapping("/user/reject/friend")
    public String rejectFriendsRequest(@RequestBody AddFriendsRequest addFriendsRequest) {
        try{
            UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userService.rejectFriendsRequest(
                    userAccount.getUser().getId(),
                    addFriendsRequest.getFriendId()
            );
            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
        return "Not ok";
    }

    @DeleteMapping("/user/delete/friend")
    public String deleteFriend(@RequestBody AddFriendsRequest addFriendsRequest) {
        try{
            UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userService.deleteFriend(
                    userAccount.getUser().getId(),
                    addFriendsRequest.getFriendId()
            );
            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
        return "Not ok";
    }
}
