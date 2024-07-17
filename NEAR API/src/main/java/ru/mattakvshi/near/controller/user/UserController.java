package ru.mattakvshi.near.controller.user;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.mattakvshi.near.controller.BaseController;
import ru.mattakvshi.near.dto.actions.AddFriendsRequest;
import ru.mattakvshi.near.dto.actions.SubscribeRequest;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.service.UserService;

@Slf4j
@Tag(name = "UserBaseController")
@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;


}
