package ru.mattakvshi.near.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mattakvshi.near.config.security.community.CommunityJWTProvider;
import ru.mattakvshi.near.config.security.user.UserJWTProvider;
import ru.mattakvshi.near.dto.CommunityRegistrationRequest;
import ru.mattakvshi.near.dto.UserRegistrationRequest;
import ru.mattakvshi.near.service.impl.CommunityAccountService;
import ru.mattakvshi.near.service.impl.UserAccountService;

@RestController
public class AuthorizationController extends BaseController{

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private CommunityAccountService communityAccountService;

    @Autowired
    private UserJWTProvider userJWTProvider;

    @Autowired
    private CommunityJWTProvider communityJWTProvider;

    @PostMapping("/signup/account")
    public String registerUser(@RequestBody @Valid UserRegistrationRequest userRegistrationRequest) {
        userAccountService.saveUser(userRegistrationRequest.toAccount());
        return "OK";
    }

    @PostMapping("/login/account")

    @PostMapping("/signup/community")
    public String registerUser(@RequestBody @Valid CommunityRegistrationRequest communityRegistrationRequest) {
        communityAccountService.saveCommunity(communityRegistrationRequest.toAccount());
        return "OK";
    }

    @PostMapping("/login/community")


}
