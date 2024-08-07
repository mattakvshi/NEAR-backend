package ru.mattakvshi.near.controller.user;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.mattakvshi.near.controller.BaseController;
import ru.mattakvshi.near.dto.actions.AddFriendsRequest;
import ru.mattakvshi.near.dto.actions.SubscribeRequest;
import ru.mattakvshi.near.dto.community.UserDTOForCommunity;
import ru.mattakvshi.near.dto.user.UserDTOForUser;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.service.UserService;

import java.util.UUID;

@Slf4j
@Tag(name = "UserBaseController")
@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/get")
    public ResponseEntity<UserDTOForUser> getUser(@RequestParam UUID id) {
        var userDTO = userService.getUserDTO(id);
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }
        return new ResponseEntity<>(userDTO, HttpStatusCode.valueOf(200));
    }


}
