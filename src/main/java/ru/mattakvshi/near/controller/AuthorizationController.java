package ru.mattakvshi.near.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.mattakvshi.near.config.security.JWTProvider;
import ru.mattakvshi.near.config.security.JWTProviderOld;
import ru.mattakvshi.near.dto.AuthRequests;
import ru.mattakvshi.near.dto.AuthResponse;
import ru.mattakvshi.near.dto.CommunityRegistrationRequest;
import ru.mattakvshi.near.dto.UserRegistrationRequest;
import ru.mattakvshi.near.entity.User;
import ru.mattakvshi.near.entity.auth.CommunityAccount;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.service.CommunityAccountService;
import ru.mattakvshi.near.service.CommunityService;
import ru.mattakvshi.near.service.UserAccountService;
import ru.mattakvshi.near.service.UserService;

import java.util.UUID;

@RestController
public class AuthorizationController extends BaseController{

    @Autowired
    @Lazy
    private UserAccountService userAccountService;

    @Autowired
    @Lazy
    private CommunityAccountService communityAccountService;

    @Autowired
    @Lazy
    private JWTProvider jwtProvider;


    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    @Lazy
    private CommunityService communityService;

    //USER ACCOUNT

    @PostMapping("/signup/account")
    public String registerUser(@RequestBody @Valid UserRegistrationRequest userRegistrationRequest) {
        UserAccount userAccount = userRegistrationRequest.toAccount();
        userAccountService.saveUser(userAccount);
        return "OK" + userService.saveUser(userAccount.getUser());
    }

    @PostMapping("/login/account")
    public AuthResponse authUser(@RequestBody AuthRequests request){
        UserAccount userAccount = userAccountService.findByEmailAndPassword(request.getEmail(), request.getPassword());
        String token = jwtProvider.generateToken(userAccount.getEmail());
        return new AuthResponse(token, userAccount.getId());
    }

//    @GetMapping("/user/{id}")
//    public ResponseEntity<User> getCurrentUser(@PathVariable UUID id) {
//        //UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        UserAccount userAccount = userAccountService.findById(id);
//        return ResponseEntity.ok(userAccount.getUser());
//    }

    @GetMapping("/user/me")
    public ResponseEntity<User> getCurrentUser() {
        Authentication userAccount = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok((User) userAccount.getPrincipal());
    }

    @PostMapping("/user/refresh")
    public ResponseEntity<AuthResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
        try {
            final JwtResponse token = authService.refresh(request.getRefreshToken());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            log.info("Auth error: " + e);
            return ResponseEntity.internalServerError().body(new JwtResponse(null, e.getMessage()));
        }
    }

    //COMMUNITY ACCOUNT

//    @PostMapping("/signup/community")
//    public String registerCommunity(@RequestBody @Valid CommunityRegistrationRequest communityRegistrationRequest) {
//        CommunityAccount communityAccount = communityRegistrationRequest.toAccount();
//        communityAccountService.saveCommunity(communityAccount);
//        return "OK" + communityService.saveCommunity(communityAccount.getCommunity());
//    }
//
//    @PostMapping("/login/community")
//    public AuthResponse authCommunity(@RequestBody AuthRequests request){
//        CommunityAccount communityAccount = communityAccountService.findByEmailAndPassword(request.getEmail(), request.getPassword());
//        String token = jwtProvider.generateToken(communityAccount.getEmail());
//        return new AuthResponse(token, communityAccount.getId());
    }


}
