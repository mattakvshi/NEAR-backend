package ru.mattakvshi.near.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.mattakvshi.near.dto.actions.group.GroupFindRequest;
import ru.mattakvshi.near.dto.actions.group.GroupRequest;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.service.UserGroupService;

@Slf4j
@RestController
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    @PostMapping("/user/group/create")
    public String addNewGroup(@RequestBody GroupRequest groupRequest) {
        try{
            UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userGroupService.saveNewGroup(userAccount.getUser(), groupRequest);
            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
        return "Not ok";
    }

    @PutMapping("/user/group/update")
    public String updateGroup(@RequestBody GroupFindRequest groupFindRequest) {
        try{
            UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userGroupService.updateGroup(userAccount.getUser(), groupFindRequest);
            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
        return "Not ok";
    }

    @DeleteMapping("/user/group/delete")
    public String deleteGroup(@RequestBody  GroupFindRequest groupFindRequest) {
        try{
            UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userGroupService.deleteGroup(userAccount.getUser(), groupFindRequest);
            return "OK";
        } catch (Exception e) {
            log.info("Exception: " + e);
        }
        return "Not ok";
    }
}
