package ru.mattakvshi.near.controller.user;


import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mattakvshi.near.dto.actions.AddFriendsRequest;
import ru.mattakvshi.near.dto.actions.NotificationTemplateRequest;
import ru.mattakvshi.near.service.UserService;

@Log
@RestController
public class UserTemplateController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/template/create")
    public String createTemplate(@RequestBody NotificationTemplateRequest notificationTemplateRequest) {
        try{
            
            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
        return "Not ok";
    }

    @PutMapping("/user/template/update")
    public String updateTemplate(@RequestBody NotificationTemplateRequest notificationTemplateRequest) {
        try{

            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
        return "Not ok";
    }

    @DeleteMapping("/user/template/delete")
    public String deleteTemplate(@RequestBody NotificationTemplateRequest notificationTemplateRequest) {
        try{

            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
        return "Not ok";
    }

    @PostMapping("/user/template/send")
    public String sendTemplate(@RequestBody NotificationTemplateRequest notificationTemplateRequest) {
        try{

            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
        return "Not ok";
    }

}
