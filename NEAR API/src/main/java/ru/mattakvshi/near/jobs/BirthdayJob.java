package ru.mattakvshi.near.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mattakvshi.near.dao.UserDAO;
import ru.mattakvshi.near.entity.base.User;

import java.util.UUID;

@Component
public class BirthdayJob implements Job {

    @Autowired
    UserDAO userDAO;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getMergedJobDataMap();
        UUID userId = UUID.fromString(dataMap.getString("userId"));
        User user = userDAO.findById(userId);
        user.setAge(user.getAge() + 1);
        userDAO.saveUser(user);
    }


}
