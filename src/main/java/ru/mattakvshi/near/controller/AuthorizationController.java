package ru.mattakvshi.near.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mattakvshi.near.config.security.community.CommunityJWTProvider;
import ru.mattakvshi.near.config.security.user.UserJWTProvider;
import ru.mattakvshi.near.dto.AuthRequests;
import ru.mattakvshi.near.dto.AuthResponse;
import ru.mattakvshi.near.dto.CommunityRegistrationRequest;
import ru.mattakvshi.near.dto.UserRegistrationRequest;
import ru.mattakvshi.near.entity.auth.CommunityAccount;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.service.CommunityAccountService;
import ru.mattakvshi.near.service.UserAccountService;
import ru.mattakvshi.near.service.UserService;

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

    @Autowired
    private UserService userService;

    @PostMapping("/signup/account")
    public String registerUser(@RequestBody @Valid UserRegistrationRequest userRegistrationRequest) {
        UserAccount userAccount = userRegistrationRequest.toAccount();
        userAccountService.saveUser(userAccount);
        userService.saveUser(userAccount.getUser());
        return "OK";
    }

    @PostMapping("/login/account")
    public AuthResponse authUser(@RequestBody AuthRequests request){
        UserAccount userAccount = userAccountService.findByEmailAndPassword(request.getEmail(), request.getPassword());
        String token = userJWTProvider.generateToken(userAccount.getEmail());
        return new AuthResponse(token);
    }

    @PostMapping("/signup/community")
    public String registerCommunity(@RequestBody @Valid CommunityRegistrationRequest communityRegistrationRequest) {
        communityAccountService.saveCommunity(communityRegistrationRequest.toAccount());
        return "OK";
    }

    @PostMapping("/login/community")
    public AuthResponse authCommunity(@RequestBody AuthRequests request){
        CommunityAccount communityAccount = communityAccountService.findByEmailAndPassword(request.getEmail(), request.getPassword());
        String token = communityJWTProvider.generateToken(communityAccount.getEmail());
        return new AuthResponse(token);
    }


}
