package ru.mattakvshi.EmailWorker.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EmailMessage.class, name = "Email"),
})
@Getter
@Setter
public class NotificationMessage {
    private String templateName;

    private String owner;

    private String message;

    private String emergencyType;

    @Override
    public String toString() {
        return templateName + "\n" +  message +  "\nТип чрезвычайной ситуации: " + emergencyType + "\n От: " + owner + "\n";
    }
}
