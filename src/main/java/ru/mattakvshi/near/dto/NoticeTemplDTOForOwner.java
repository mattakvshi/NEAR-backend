package ru.mattakvshi.near.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import ru.mattakvshi.near.entity.EmergencyTypes;
import ru.mattakvshi.near.entity.NotificationTemplate;
import ru.mattakvshi.near.entity.base.TemplateOwner;

import java.util.UUID;

@Data
public class NoticeTemplDTOForOwner {

    private UUID id;

    private String templateName;

    private String message;

    private EmergencyTypes emergencyType;

    public static NoticeTemplDTOForOwner from(NotificationTemplate template) {
        NoticeTemplDTOForOwner dto = new NoticeTemplDTOForOwner();
        dto.setId(template.getId());
        dto.setTemplateName(template.getTemplateName());
        dto.setMessage(template.getMessage());
        dto.setEmergencyType(template.getEmergencyType());
        return dto;
    }
}
