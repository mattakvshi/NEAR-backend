package ru.mattakvshi.near.service;

import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContext;
import ru.mattakvshi.near.dto.user.UserDTOForUser;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.entity.base.User;

import java.util.UUID;

public interface UserService {

    @Transactional
    UUID saveUserForFirstTime(User user);

    @Transactional
    UUID saveUser(User user);

    @Transactional
    User getUser(UUID userId);

    //@Cacheable(value = "getUserByContext", key = "#id")
    UserDTOForUser getUserDTO(UUID id);

    void updateDeviceToken(UUID userId, String deviceToken);
}
