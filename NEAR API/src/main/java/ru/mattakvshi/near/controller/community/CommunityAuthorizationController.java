package ru.mattakvshi.near.controller.community;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mattakvshi.near.controller.BaseController;
import ru.mattakvshi.near.dto.auth.AuthRequests;
import ru.mattakvshi.near.dto.auth.AuthResponse;
import ru.mattakvshi.near.dto.auth.CommunityRegistrationRequest;
import ru.mattakvshi.near.dto.auth.RefreshJwtRequest;
import ru.mattakvshi.near.entity.auth.CommunityAccount;
import ru.mattakvshi.near.service.CommunityAccountService;
import ru.mattakvshi.near.service.CommunityService;

@Log
@RestController
public class CommunityAuthorizationController extends BaseController {

    @Autowired
    @Lazy
    private CommunityAccountService communityAccountService;

    @Autowired
    @Lazy
    private CommunityService communityService;

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
        return ResponseEntity.ok(communityAccountService.getCommunityByContext(SecurityContextHolder.getContext()));
    }

}
