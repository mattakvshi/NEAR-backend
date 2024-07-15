package ru.mattakvshi.near.controller.user;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mattakvshi.near.controller.BaseController;
import ru.mattakvshi.near.dto.actions.SubscribeRequest;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.service.SubsService;
import ru.mattakvshi.near.service.UserService;

@Log
@RestController
public class UserSubscribeController extends BaseController {

    @Autowired
    private SubsService subsService;

    @PostMapping("/user/subscribe")
    public String subscribeUserToCommunity(@RequestBody SubscribeRequest subscribeRequest) {
        try {

            UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            subsService.subscribeUserToCommunity(
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
            subsService.cancelSubscriptionUserToCommunity(
                    userAccount.getUser().getId(),
                    subscribeRequest.getCommunityId()
            );
            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
        return "Not ok";
    }
}
