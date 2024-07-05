package ru.mattakvshi.near.controller.user;


import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.mattakvshi.near.dto.actions.NotificationTemplateRequest;
import ru.mattakvshi.near.dto.actions.SendTemplateRequest;
import ru.mattakvshi.near.entity.NotificationTemplate;
import ru.mattakvshi.near.entity.base.TemplateOwner;
import ru.mattakvshi.near.service.NotificationTemplateService;
import ru.mattakvshi.near.helpers.NotificationTemplateBuilder;

@Log
@RestController
public class UserTemplateController {

    @Autowired
    private NotificationTemplateService notificationTemplateService;

    @PostMapping(value = {"/user/template/create", "/community/template/create"})
    public String createTemplate(@RequestBody NotificationTemplateRequest notificationTemplateRequest) {
        try{
            Authentication account = (Authentication) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            NotificationTemplate notificationTemplate = NotificationTemplateBuilder.from((TemplateOwner) account.getDetails(), notificationTemplateRequest);
            notificationTemplateService.saveTemplate(notificationTemplate);
            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
        return "Not ok";
    }

    @PutMapping(value = {"/user/template/update", "/community/template/update"})
    public String updateTemplate(@RequestBody NotificationTemplateRequest notificationTemplateRequest) {
        try{
            Authentication account = (Authentication) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            NotificationTemplate notificationTemplate = NotificationTemplateBuilder.from((TemplateOwner) account.getDetails(), notificationTemplateRequest);
            notificationTemplateService.updateTemplate(notificationTemplate);

            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
        return "Not ok";
    }

    @DeleteMapping(value = {"/user/template/delete", "/community/template/delete"})
    public String deleteTemplate(@RequestBody NotificationTemplateRequest notificationTemplateRequest) {
        try{
            Authentication account = (Authentication) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            NotificationTemplate notificationTemplate = NotificationTemplateBuilder.from((TemplateOwner) account.getDetails(), notificationTemplateRequest);
            notificationTemplateService.deleteTemplate(notificationTemplate);

            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
        return "Not ok";
    }

    @PostMapping(value = {"/user/template/send", "/community/template/send"})
    public String sendTemplate(@RequestBody SendTemplateRequest sendTemplateRequest) {
        try{

            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
        return "Not ok";
    }

}
