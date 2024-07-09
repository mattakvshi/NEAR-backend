package ru.mattakvshi.near.dto.actions;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import ru.mattakvshi.near.entity.NotificationTemplate;
import ru.mattakvshi.near.entity.base.User;

import java.util.List;
import java.util.UUID;

@Data
public class SendTemplateRequest {

    @JsonSerialize(using = ToStringSerializer.class)
    private UUID templateId;

    @JsonSerialize(using = ToStringSerializer.class)
    private List<UUID> recipients;
}