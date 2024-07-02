package ru.mattakvshi.near.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.mattakvshi.near.dto.auth.*;
import ru.mattakvshi.near.entity.auth.CommunityAccount;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.service.CommunityAccountService;
import ru.mattakvshi.near.service.CommunityService;
import ru.mattakvshi.near.service.UserAccountService;
import ru.mattakvshi.near.service.UserService;

@RestController
@Log
public class AuthorizationController extends BaseController{

    @Autowired
    @Lazy
    private UserAccountService userAccountService;

    @Autowired
    @Lazy
    private CommunityAccountService communityAccountService;

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    @Lazy
    private CommunityService communityService;


    //USER ACCOUNT ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/signup/account")
    public String registerUser(@RequestBody @Valid UserRegistrationRequest userRegistrationRequest) {
        UserAccount userAccount = userRegistrationRequest.toAccount();
        userAccountService.saveUser(userAccount);
        return "OK" + userService.saveUserForFirstTime(userAccount.getUser());
    }

    @PostMapping("/login/account")
    public ResponseEntity<AuthResponse> authUser(@RequestBody AuthRequests authRequest){
        try {
            final AuthResponse authResponse = userAccountService.login(authRequest);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            log.info("Auth error: " + e);
            return ResponseEntity.internalServerError().body(new AuthResponse(null, e.getMessage(), null));
        }
    }

    @PostMapping("/token/account")
    public ResponseEntity<AuthResponse> getNewAccessTokenForUser(@RequestBody RefreshJwtRequest refreshJwtRequest){
        try {
            final AuthResponse authResponse = userAccountService.getAccessToken(refreshJwtRequest.getRefreshToken());
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            log.info("Auth error: " + e);
            return ResponseEntity.internalServerError().body(new AuthResponse(null, e.getMessage(), null));
        }
    }

    @PostMapping("/user/refresh")
    public ResponseEntity<AuthResponse> getNewRefreshTokenUser(@RequestBody RefreshJwtRequest refreshJwtRequest) {
        try {
            final AuthResponse authResponse = userAccountService.refresh(refreshJwtRequest.getRefreshToken());
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            log.info("Auth error: " + e);
            return ResponseEntity.internalServerError().body(new AuthResponse(null, e.getMessage(), null));
        }
    }

    @GetMapping("/user/me")
    @Transactional
    public ResponseEntity<Object> getCurrentUser() {
        UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userAccount.getPrincipal());
    }



    //COMMUNITY ACCOUNT ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/signup/community")
    public String registerCommunity(@RequestBody @Valid CommunityRegistrationRequest communityRegistrationRequest) {
        CommunityAccount communityAccount = communityRegistrationRequest.toAccount();
        communityAccountService.saveCommunity(communityAccount);
        return "OK" + communityService.saveCommunity(communityAccount.getCommunity());
    }

    @PostMapping("/login/community")
    public ResponseEntity<AuthResponse> authCommunity(@RequestBody AuthRequests authRequest){
        try {
            final AuthResponse authResponse = communityAccountService.login(authRequest);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            log.info("Auth error: " + e);
            return ResponseEntity.internalServerError().body(new AuthResponse(null, e.getMessage(), null));
        }
    }


    @PostMapping("/token/community")
    public ResponseEntity<AuthResponse> getNewAccessTokenForCommunity(@RequestBody RefreshJwtRequest refreshJwtRequest){
        try {
            final AuthResponse authResponse = communityAccountService.getAccessToken(refreshJwtRequest.getRefreshToken());
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            log.info("Auth error: " + e);
            return ResponseEntity.internalServerError().body(new AuthResponse(null, e.getMessage(), null));
        }
    }

    @PostMapping("/community/refresh")
    public ResponseEntity<AuthResponse> getNewRefreshTokenCommunity(@RequestBody RefreshJwtRequest refreshJwtRequest) {
        try {
            final AuthResponse authResponse = communityAccountService.refresh(refreshJwtRequest.getRefreshToken());
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            log.info("Auth error: " + e);
            return ResponseEntity.internalServerError().body(new AuthResponse(null, e.getMessage(), null));
        }
    }

    @GetMapping("/community/me")
    @Transactional
    public ResponseEntity<Object> getCurrentCommunity() {
        CommunityAccount communityAccount = (CommunityAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(communityAccount.getPrincipal());
    }


}
