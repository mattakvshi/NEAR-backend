package ru.mattakvshi.near.controller.user;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.Lazy;
import ru.mattakvshi.near.controller.BaseController;
import ru.mattakvshi.near.dto.actions.AddFriendsRequest;
import ru.mattakvshi.near.dto.actions.SubscribeRequest;
import ru.mattakvshi.near.dto.community.UserDTOForCommunity;
import ru.mattakvshi.near.dto.user.CommunityDTOForUser;
import ru.mattakvshi.near.dto.user.UserDTOForUser;
import ru.mattakvshi.near.dto.user.UserUpdateRequest;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.service.UserService;
import ru.mattakvshi.near.service.UserAccountService;
import ru.mattakvshi.near.dto.user.DeviceTokenRequest;

import java.util.UUID;


@Slf4j
@Tag(name = "UserBaseController")
@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    @Lazy
    private UserAccountService userAccountService;

    @GetMapping("/user/get")
    public ResponseEntity<UserDTOForUser> getUser(@RequestParam UUID id) {
        var userDTO = userService.getUserDTO(id);
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }
        return new ResponseEntity<>(userDTO, HttpStatusCode.valueOf(200));
    }

    @Operation(
            summary = "Эндпоинт для обновления данных пользователя",
            description = "Можно хоть один элемент передавать в JSON хоть все, передавай только то, что поменялось, такой вот вари оптимизации."
    )
    @PatchMapping("/user/update")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequest request) {
        try {
            UUID userId = userAccountService.getCurrentUserUUID();
            userService.updateUser(userId, request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Ошибка обновления данных пользователя", e);
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }


    @Operation(
            summary = "Эндпоинт для обновления Device Token",
            description = "Данный эндпоинт необходим исключительно для мобильного клиента. Он необходим в целях смены Device Token " +
                    "и привязки конкретного устройства к пользователю в системе."
    )
     @PostMapping("/user/update-device-token")
    public ResponseEntity<?> updateDeviceToken(@RequestBody DeviceTokenRequest request) {
        try {

            userService.updateDeviceToken(userAccountService.getCurrentUserUUID(), request.getDeviceToken());
 
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Ошибка обновления токена", e);
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @Operation(
            summary = "Эндпоинт для получения выбранных пользователем каналов оповещения"
    )
    @GetMapping("/user/get-notification-options")
    public ResponseEntity<?> getNotificationOptionsForUser() {
        try{
            return ResponseEntity.ok(userService.getUser(userAccountService.getCurrentUserUUID()).getSelectedOptions());
        } catch (AuthException ae) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }
    }

    @GetMapping("/community/all")
    @Operation(
            summary = "Получить список пользователей с пагинацией и поиском.",
            description = "На данный момент эндпоинт принимает 3 параметра: " +
                    "search - тег для поиска по имени или описнию всообщества, " +
                    "page - получаемая страница из набора всех сообществ, " +
                    "size - колличество элементов на странице"
    )
    public ResponseEntity<Page<UserDTOForUser>> getUsers(
            @RequestParam(name = "search", required = false) String searchQuery,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        Page<UserDTOForUser> users = userService.searchUsers(searchQuery, page, size);
        return ResponseEntity.ok(users);
    }


}
