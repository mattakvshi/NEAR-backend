package ru.mattakvshi.grpc_gateway.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.UUID;

@Data
public class NotificationTemplateDTO {

    @JsonSerialize(using = ToStringSerializer.class)
    private UUID id;

    private String templateName;

    private String message;

    private EmergencyTypes emergencyType;

}
