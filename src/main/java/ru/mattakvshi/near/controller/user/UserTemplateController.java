package ru.mattakvshi.near.controller.user;


import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.mattakvshi.near.dto.actions.AddFriendsRequest;
import ru.mattakvshi.near.dto.actions.NotificationTemplateRequest;
import ru.mattakvshi.near.dto.actions.SendTemplateRequest;
import ru.mattakvshi.near.entity.NotificationTemplate;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.service.NotificationTemplateService;
import ru.mattakvshi.near.service.UserService;
import ru.mattakvshi.near.utils.NotificationTemplateBuilder;

@Log
@RestController
public class UserTemplateController {

    @Autowired
    private NotificationTemplateService notificationTemplateService;

    @PostMapping("/user/template/create")
    public String createTemplate(@RequestBody NotificationTemplateRequest notificationTemplateRequest) {
        try{
            UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            NotificationTemplate notificationTemplate = NotificationTemplateBuilder.from(userAccount.getUser(), notificationTemplateRequest);
            notificationTemplateService.saveTemplate(notificationTemplate);
            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
        return "Not ok";
    }

    @PutMapping("/user/template/update")
    public String updateTemplate(@RequestBody NotificationTemplateRequest notificationTemplateRequest) {
        try{
            UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            NotificationTemplate notificationTemplate = NotificationTemplateBuilder.from(userAccount.getUser(), notificationTemplateRequest);
            notificationTemplateService.updateTemplate(notificationTemplate);

            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
        return "Not ok";
    }

    @DeleteMapping("/user/template/delete")
    public String deleteTemplate(@RequestBody NotificationTemplateRequest notificationTemplateRequest) {
        try{
            UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            NotificationTemplate notificationTemplate = NotificationTemplateBuilder.from(userAccount.getUser(), notificationTemplateRequest);
            notificationTemplateService.deleteTemplate(notificationTemplate);

            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
        return "Not ok";
    }

    @PostMapping("/user/template/send")
    public String sendTemplate(@RequestBody SendTemplateRequest sendTemplateRequest) {
        try{

            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
        return "Not ok";
    }

}
