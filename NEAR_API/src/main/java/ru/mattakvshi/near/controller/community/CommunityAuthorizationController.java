package ru.mattakvshi.near.controller.community;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.mattakvshi.near.controller.BaseController;
import ru.mattakvshi.near.dto.auth.*;
import ru.mattakvshi.near.entity.auth.CommunityAccount;
import ru.mattakvshi.near.service.CommunityAccountService;
import ru.mattakvshi.near.service.CommunityService;

import java.util.UUID;

@Slf4j
@Tag(name = "CommunityAuthController")
@RestController
public class CommunityAuthorizationController extends BaseController {

    @Autowired
    @Lazy
    private CommunityAccountService communityAccountService;

    @Autowired
    @Lazy
    private CommunityService communityService;

    @PostMapping("/signup/community")
    public ResponseEntity<?> registerCommunity(@RequestBody @Valid CommunityRegistrationRequest communityRegistrationRequest) {
        CommunityAccount communityAccount = communityRegistrationRequest.toAccount();
        communityAccountService.saveCommunity(communityAccount);

        //Отправляем ссылку подтверждения на почту пользователю
        communityAccountService.sendVerificationEmail(communityAccount);
        return ResponseEntity.ok("Регистрация прошла успешно! "
                + "Письмо с подтверждением отправлено на "
                + communityAccount.getEmail());
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


    //Проверка имейла и токена
    @GetMapping("/verify-email-community")
    public ResponseEntity<?> verifyEmail(@RequestParam String token) {
        try {
            UUID uuidToken = UUID.fromString(token.trim()); // Удаляем лишние пробелы
            if (communityAccountService.verifyEmail(uuidToken)) {
                return ResponseEntity.ok("Почта подтверждена");
            } else {
                return ResponseEntity.badRequest().body("Неверный или истекший токен");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Некорректный формат токена");
        }
    }

    @PostMapping("/community/change-email")
    public ResponseEntity<?> changeEmail(@RequestBody ChangeEmailRequest request) throws AuthException {
        try {
            UUID communityAccountId = communityAccountService.getCurrentCommunityAccountUUID();
            communityAccountService.requestEmailChange(communityAccountId, request.getNewEmail());
            return ResponseEntity.ok("Ссылка для подтверждения отправлена на старую почту");
        } catch (EntityNotFoundException ex) {
            log.error("Сообщество не найдено", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Сообщество не найдено");
        } catch (Exception ex) {
            log.error("Ошибка при смене email", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка сервера");
        }
    }

    @GetMapping("/confirm-email-change-community")
    public ResponseEntity<?> confirmEmailChange(@RequestParam UUID token) {
        if (communityAccountService.verifyEmailChange(token)) {
            return ResponseEntity.ok("Почта успешно изменена");
        } else {
            return ResponseEntity.badRequest().body("Неверный или истекший токен");
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
        try{
            return ResponseEntity.ok(communityService.getCommunityDTO(communityAccountService.getCurrentCommunityUUID()));
        } catch (AuthException ae) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }
    }

}
