package ru.mattakvshi.near.dto.notice;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.mattakvshi.near.entity.EmergencyTypes;
import ru.mattakvshi.near.entity.base.TemplateOwner;
import ru.mattakvshi.near.entity.base.User;

@Getter
@Setter
public class NotificationMessage {
    private String templateName;

    private TemplateOwner owner;

    private String message;

    private EmergencyTypes emergencyType;
}
