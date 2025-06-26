package ru.mattakvshi.near.controller.user;

import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.mattakvshi.near.controller.BaseController;
import ru.mattakvshi.near.dto.auth.*;
import ru.mattakvshi.near.dto.user.UserDTOForUser;
import ru.mattakvshi.near.entity.auth.EmailVerificationToken;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.service.UserAccountService;
import ru.mattakvshi.near.service.UserService;

import java.util.UUID;

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
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegistrationRequest userRegistrationRequest) {
        UserAccount userAccount = userRegistrationRequest.toAccount();
        userAccountService.saveUser(userAccount);

        //Отправляем ссылку подтверждения на почту пользователю
        userAccountService.sendVerificationEmail(userAccount);
        return ResponseEntity.ok("Регистрация прошла успешно! "
                + "Письмо с подтверждением отправлено на "
                + userAccount.getEmail());
    }

    @Operation(
            summary = "Эндпоинт для авторизации пользовательского аккаунта, после отправки пороля и логина возвращается либо ошибка авториации либо токены. ",
            description = " На вход подаётся DTO AuthRequests, оно содержит два поля email и password, пароль прогоняется через кодировщик, " +
                    "и по почте и паролю находится соответсвующая сущность, если она есть то так же по почте и паролю создаём access и refresh токены, и возвращаем в ответ " +
                    "в формате DTO AuthResponse"
    )
    @PostMapping("/login/account")
    public ResponseEntity<AuthResponse> authUser(@RequestBody AuthRequests authRequest){
        try {
            final AuthResponse authResponse = userAccountService.login(authRequest);
            return ResponseEntity.ok(authResponse);
        } catch (DisabledException e) {
            log.info("Auth error: " + e);
            return ResponseEntity.status(403).body(new AuthResponse(null, "Email не подтвержден. Письмо с подтверждением отправлено на почту повторно.", null));
        } catch (AuthException e) {
            log.info("Auth error: " + e);
            return ResponseEntity.status(401).body(new AuthResponse(null, e.getMessage(), null));
        } catch (Exception e) {
            log.info("Auth error: " + e);
            return ResponseEntity.status(500).body(new AuthResponse(null, e.getMessage(), null));
        }
    }

    //Проверка имейла и токена
    @GetMapping("/verify-email-user")
    public ResponseEntity<?> verifyEmail(@RequestParam String token) {
        try {
            UUID uuidToken = UUID.fromString(token.trim()); // Удаляем лишние пробелы
            if (userAccountService.verifyEmail(uuidToken)) {
                return ResponseEntity.ok("Почта подтверждена");
            } else {
                return ResponseEntity.badRequest().body("Неверный или истекший токен");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Некорректный формат токена");
        }
    }

    @PostMapping("/user/change-email")
    public ResponseEntity<?> changeEmail(@RequestBody ChangeEmailRequest request) throws AuthException {
        try {
            UUID userAccountId = userAccountService.getCurrentUserAccountUUID();
            userAccountService.requestEmailChange(userAccountId, request.getNewEmail());
            return ResponseEntity.ok("Ссылка для подтверждения отправлена на старую почту");
        } catch (EntityNotFoundException ex) {
            log.error("Пользователь не найден", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
        } catch (Exception ex) {
            log.error("Ошибка при смене email", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка сервера");
        }
    }

    @GetMapping("/confirm-email-change-user")
    public ResponseEntity<?> confirmEmailChange(@RequestParam UUID token) {
        if (userAccountService.verifyEmailChange(token)) {
            return ResponseEntity.ok("Почта успешно изменена");
        } else {
            return ResponseEntity.badRequest().body("Неверный или истекший токен");
        }
    }


    @Operation(
            summary = "Эндпоинт для получения актуального access токена по refresh",
            description = ""
    )
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

    @Operation(
            summary = "Эндпоинт для получения новой пары access/refresh токенов.",
            description = ""
    )
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

    @Operation(
            summary = "Эндпоинт для получения информации о авторизованном пользователе. ",
            description = "В Authorization: Bearer <token> передаём токен и из SecurityContextHolder получаем пользователя."
    )
    @GetMapping("/user/me")
    @Transactional
    public ResponseEntity<Object> getCurrentUser() {
        try{
            return ResponseEntity.ok(userService.getUserDTO(userAccountService.getCurrentUserUUID()));
        } catch (AuthException ae) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }
    }


}
