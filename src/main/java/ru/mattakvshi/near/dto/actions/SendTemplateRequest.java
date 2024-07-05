package ru.mattakvshi.near.dto.actions;

import lombok.Data;
import ru.mattakvshi.near.entity.NotificationTemplate;
import ru.mattakvshi.near.entity.base.User;

import java.util.List;

@Data
public class SendTemplateRequest {

    private NotificationTemplate notificationTemplate;

    private List<User> recipients;
}
