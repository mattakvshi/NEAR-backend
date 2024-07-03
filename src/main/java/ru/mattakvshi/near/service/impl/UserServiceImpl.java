package ru.mattakvshi.near.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.java.Log;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dao.CommunityDAO;
import ru.mattakvshi.near.dao.UserDAO;
import ru.mattakvshi.near.entity.Community;
import ru.mattakvshi.near.entity.User;
import ru.mattakvshi.near.jobs.BirthdayJob;
import ru.mattakvshi.near.service.UserService;

import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Log
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
            log.info("Error starting scheduler: " + schedulerException.getMessage());
        }

        return uuid;
    }

    @Override
    public UUID saveUser(User user) {
        return userDAO.saveUser(user);
    }

    @Override
    @Transactional
    public void subscribeUserToCommunity(UUID userId, UUID communityId) {
        User user = userDAO.findById(userId);
        Community community = communityDAO.findById(communityId);

        if(user != null && community != null) {
            // Проверяем, не подписан ли уже пользователь на сообщество
            if(!user.getSubscriptions().contains(community)) {
                user.getSubscriptions().add(community);
                community.getSubscribers().add(user);

                userDAO.saveUser(user);
                communityDAO.saveCommunity(community);
            } else {
                // Обработка ситуации, когда пользователь уже подписан на сообщество
                throw new RuntimeException("User is already subscribed to the community");
            }

        } else if (user == null) {
            throw new RuntimeException("User not found");
        } else {
            throw new RuntimeException("Community not found");
        }
    }

    @Override
    @Transactional
    public void cancelSubscriptionUserToCommunity(UUID userId, UUID communityId) {
        User user = userDAO.findById(userId);
        Community community = communityDAO.findById(communityId);

        if(user != null && community != null) {
            if(user.getSubscriptions().contains(community)) {
                user.getSubscriptions().remove(community);
                community.getSubscribers().remove(user);

                userDAO.saveUser(user);
                communityDAO.saveCommunity(community);

            } else {
                // Обработка ситуации, когда пользователь уже подписан на сообщество
                throw new RuntimeException("User is not subscribed to the community");
            }

        } else if (user == null) {
            throw new RuntimeException("User not found");
        } else {
            throw new RuntimeException("Community not found");
        }
    }

    @Override
    public void addNewFriend(UUID userId, UUID friend1Id) {
        User user = userDAO.findById(userId);
        User friend = userDAO.findById(friend1Id);

        if(user != null && friend != null) {
            user.getFriends().add(friend);
            friend.getFriends().add(user);

            userDAO.saveUser(user);
            userDAO.saveUser(friend);

        } else if (user == null) {
            throw new RuntimeException("User not found");
        } else {
            throw new RuntimeException("Friend not found");
        }
    }
}
