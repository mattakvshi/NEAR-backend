package ru.mattakvshi.near.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.mattakvshi.near.controller.BaseController;
import ru.mattakvshi.near.dto.auth.*;
import ru.mattakvshi.near.dto.user.UserDTOForUser;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.service.UserAccountService;
import ru.mattakvshi.near.service.UserService;

@Slf4j
@Tag(name = "UserAuthController")
@RestController
public class UserAuthorizationController extends BaseController {

    @Autowired
    @Lazy
    private UserAccountService userAccountService;

    @Autowired
    @Lazy
    private UserService userService;

    @Operation(
            summary = "Эндпоинт для регистрации пользовательского аккаунта, после отправки всех данных создаётся UserAccount, TemplateOwner, User и записываются в базу. ",
            description = " На вход подаётся DTO UserRegistrationRequest, по информации из него создаются три сущности для работы с пользователем: \n " +
                    "UserAccount - основная информация об аккаунте, пароль, email, и т.д. сущность в рамках SpringSecurity, так же для формирования JWT.\n" +
                    "TemplateOwner - родительский класс для User и Community обеспечивает обобщённое взаимодействие пользователя и сообщества с шаблонами уведомлений (NotificationTemplate).\n" +
                    "User - вся личная информация пользователя имя, возраст, страна, город, район, друзья, группы, подписки и т.д.\n"
    )
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
        //UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //return ResponseEntity.ok(userAccount.getPrincipal());
        return ResponseEntity.ok(userService.getUserDTO((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
    }

}
