package ru.mattakvshi.near.service.impl.base;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dao.CommunityDAO;
import ru.mattakvshi.near.dao.UserDAO;
import ru.mattakvshi.near.dto.user.UserDTOForUser;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.entity.base.User;
import ru.mattakvshi.near.jobs.BirthdayJob;
import ru.mattakvshi.near.service.UserService;

import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CommunityDAO communityDAO;

    @Autowired
    Scheduler scheduler;

    @Override
    @Transactional
    public UUID saveUserForFirstTime(User user) {
            //Сохраняем сущность
            UUID uuid = userDAO.saveUser(user);

        try {

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

        } catch (SchedulerException schedulerException) {
            log.error("Error starting scheduler: " + schedulerException.getMessage());
        }

        return uuid;
    }

    @Override
    public UUID saveUser(User user) {
        return userDAO.saveUser(user);
    }


    @Override
    public User getUser(UUID userId) {
        return userDAO.findById(userId);
    }

//    @Override
//    @Cacheable(value = "getUserByContext", key = "#userAccount")
//    public UserDTOForUser getUserDTO(UserAccount userAccount){
//        return UserDTOForUser.from(userAccount.getUser());
//    }
}
