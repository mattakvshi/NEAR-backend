package ru.mattakvshi.near.service;


import ru.mattakvshi.near.entity.NotificationTemplate;
import ru.mattakvshi.near.entity.base.User;

import java.util.List;

public interface NotificationDispatcher {
    void dispatch(NotificationTemplate notificationTemplate, List<User> recipients);
}
