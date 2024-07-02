package ru.mattakvshi.near.controller;


import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mattakvshi.near.dto.SubscribeRequest;
import ru.mattakvshi.near.service.UserService;

@RestController
@Log
public class UserController extends BaseController{

    @Autowired
    private UserService userService;


    @PostMapping("/user/subscribe")
    public String subscribeUserToCommunity(@RequestBody SubscribeRequest subscribeRequest) {
        //TODO: Подумать над тем чтобы не с фронта запрашивать userID, а брать просто id из текущего контекста по access токену
        try {
            userService.subscribeUserToCommunity(subscribeRequest.getUserId(), subscribeRequest.getCommunityId());
            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
       return "Not ok";
    }
}
