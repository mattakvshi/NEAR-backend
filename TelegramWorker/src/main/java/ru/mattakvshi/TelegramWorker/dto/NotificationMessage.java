package ru.mattakvshi.TelegramWorker.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TelegramMessage.class, name = "Telegram"),
})
@Getter
@Setter
public class NotificationMessage {
    private String templateName;

    private String owner;

    private String message;

    private String emergencyType;
}
