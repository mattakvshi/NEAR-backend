package ru.mattakvshi.near.dto.actions;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import ru.mattakvshi.near.entity.EmergencyTypes;
import ru.mattakvshi.near.entity.User;

import java.util.UUID;

@Data
public class NotificationTemplateRequest {

    private String templateName;

    //private User owner;

    private String message;

    private EmergencyTypes emergencyType;

}
