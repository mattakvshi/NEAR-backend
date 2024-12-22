package ru.mattakvshi.near.dto.notice;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.mattakvshi.near.entity.EmergencyTypes;
import ru.mattakvshi.near.entity.base.TemplateOwner;
import ru.mattakvshi.near.entity.base.User;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EmailMessage.class, name = "Email"),
        @JsonSubTypes.Type(value = TelegramMessage.class, name = "Telegram"),
        @JsonSubTypes.Type(value = PushMessage.class, name = "Mobile_Notification")
})
@Getter
@Setter
public class NotificationMessage {
    private String templateName;

    private String owner;

    private String message;

    private String emergencyType;
}
