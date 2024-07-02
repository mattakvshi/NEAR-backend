package ru.mattakvshi.near.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mattakvshi.near.dto.SubscribeRequest;
import ru.mattakvshi.near.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @PostMapping("/user/subscribe")
    public void subscribeUserToCommunity(@RequestBody SubscribeRequest subscribeRequest) {
        userService.subscribeUserToCommunity(subscribeRequest.getUserId(), subscribeRequest.getCommunityId());
    }
}
