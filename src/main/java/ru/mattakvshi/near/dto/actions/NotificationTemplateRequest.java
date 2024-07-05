package ru.mattakvshi.near.dto.actions;

import lombok.Data;
import ru.mattakvshi.near.entity.EmergencyTypes;

@Data
public class NotificationTemplateRequest {

    private String templateName;

    //private User owner;

    private String message;

    private EmergencyTypes emergencyType;

}
