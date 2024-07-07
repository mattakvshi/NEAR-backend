package ru.mattakvshi.near.service;


import org.springframework.security.core.Authentication;
import ru.mattakvshi.near.entity.NotificationTemplate;
import ru.mattakvshi.near.entity.base.User;

import java.util.List;

public interface NotificationDispatcher {
    void dispatch(NotificationTemplate notificationTemplate, List<User> recipients, Authentication account);
}
