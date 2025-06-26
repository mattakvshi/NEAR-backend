package ru.mattakvshi.near.service;

import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContext;
import ru.mattakvshi.near.dto.user.UserDTOForUser;
import ru.mattakvshi.near.dto.user.UserUpdateRequest;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.entity.base.User;

import java.util.UUID;

public interface UserService {

    UUID saveUserForFirstTime(User user);

    UUID saveUser(User user);

    User getUser(UUID userId);

    //@Cacheable(value = "getUserByContext", key = "#id")
    UserDTOForUser getUserDTO(UUID id);

    void updateDeviceToken(UUID userId, String deviceToken);

    void updateUser(UUID userId, UserUpdateRequest request);

    Page<UserDTOForUser> searchUsers(String query, int page, int size);
}
