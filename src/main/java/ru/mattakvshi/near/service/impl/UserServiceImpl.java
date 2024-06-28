package ru.mattakvshi.near.service.impl;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dao.UserDAO;
import ru.mattakvshi.near.entity.User;
import ru.mattakvshi.near.jobs.BirthdayJob;
import ru.mattakvshi.near.service.UserService;

import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    Scheduler scheduler;

    @Override
    public UUID saveUser(User user) {
        UUID uuid = userDAO.saveUser(user);

        // создать задание для обновления возраста
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("userId", user.getId().toString());

        JobDetail jobDetail = JobBuilder.newJob(BirthdayJob.class)
                .usingJobData(jobDataMap)
                .build();

        // задать время запуска задания - в день рождения пользователя, каждый год
        Trigger trigger = TriggerBuilder.newTrigger()
                .startAt(Date.from(user.getBirthday().atStartOfDay(ZoneId.systemDefault()).plusYears(1).toInstant()))
                .withSchedule(
                        CronScheduleBuilder.cronSchedule(
                        "0 0 0 " + user.getBirthday().getDayOfMonth()
                                    + " " + user.getBirthday().getMonth().getValue() + " ? *"
                        )
                )
                .build();

        // добавить задание в планировщик
        scheduler.scheduleJob(jobDetail, trigger);

        return uuid;
    }
}
