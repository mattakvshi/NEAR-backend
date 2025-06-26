package ru.mattakvshi.near.service.impl.base;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.spi.EntityEntry;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dao.CommunityDAO;
import ru.mattakvshi.near.dao.NotificationOptionsDAO;
import ru.mattakvshi.near.dao.UserDAO;
import ru.mattakvshi.near.dto.user.UserDTOForUser;
import ru.mattakvshi.near.dto.user.UserUpdateRequest;
import ru.mattakvshi.near.entity.NotificationOptions;
import ru.mattakvshi.near.entity.base.User;
import ru.mattakvshi.near.jobs.BirthdayJob;
import ru.mattakvshi.near.service.UserService;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private NotificationOptionsDAO notificationOptionsDAO;

    @Autowired
    Scheduler scheduler;

    @Autowired
    EntityManager entityManager;

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

    @Transactional
    @Override
    public UUID saveUser(User user) {
        var uuid = userDAO.saveUser(user);
        entityManager.flush();

        return uuid;
    }


    @Override
    public User getUser(UUID userId) {
        return userDAO.findById(userId);
    }

    @Override
    //@Cacheable(value = "getUserByContext", key = "#id")
    public UserDTOForUser getUserDTO(UUID id){
        var user = userDAO.findById(id);
        if (user == null) {
            return null;
        }
        return UserDTOForUser.from(user);
    }

    @Override
    @Transactional
    public void updateDeviceToken(UUID userId, String deviceToken) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new RuntimeException("Пользователь не найден");
        }
        user.setDeviceToken(deviceToken);
        userDAO.saveUser(user);
    }

    @Override
    @Transactional
    public void updateUser(UUID userId, UserUpdateRequest request) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new RuntimeException("Пользователь не найден");
        }

        // Обновляем поля, если они указаны в запросе
        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getBirthday() != null) {
            user.setBirthday(request.getBirthday());
            user.calculateAge();
        }
        if (request.getCountry() != null) user.setCountry(request.getCountry());
        if (request.getCity() != null) user.setCity(request.getCity());
        if (request.getDistrict() != null) user.setDistrict(request.getDistrict());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPhoneNumber() != null) user.setPhoneNumber(request.getPhoneNumber());
        if (request.getTelegramShortName() != null) user.setTelegramShortName(request.getTelegramShortName());

        // Обновление Many-to-Many связи с проверкой
        if (request.getSelectedOptions() != null) {
            List<Integer> optionIds = request.getSelectedOptions();
            List<NotificationOptions> options = notificationOptionsDAO.findAllById(optionIds);

            // Проверка существования всех ID
            if (options.size() != optionIds.size()) {
                Set<Integer> foundIds = options.stream()
                        .map(NotificationOptions::getId)
                        .collect(Collectors.toSet());
                List<Integer> missingIds = optionIds.stream()
                        .filter(id -> !foundIds.contains(id))
                        .toList();
                throw new RuntimeException("Не найдены опции уведомлений с ID: " + missingIds);
            }

            // Корректное обновление коллекции для Hibernate
            user.getSelectedOptions().clear();
            user.getSelectedOptions().addAll(options);
        }

        userDAO.saveUser(user);
    }

}
