package ru.mattakvshi.near.controller.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mattakvshi.near.controller.BaseController;
import ru.mattakvshi.near.dto.actions.AddFriendsRequest;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.service.FriendsService;
import ru.mattakvshi.near.service.UserService;

@Slf4j
@Tag(name = "FriendsController")
@RestController
public class UserFriendsController extends BaseController {

    @Autowired
    private FriendsService friendsService;

    @PostMapping("/user/request/friend")
    public String friendRequest(@RequestBody AddFriendsRequest addFriendsRequest) {
        try{
            UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            friendsService.friendRequest(
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
            friendsService.addNewFriend(
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
            friendsService.rejectFriendsRequest(
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
            friendsService.deleteFriend(
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
