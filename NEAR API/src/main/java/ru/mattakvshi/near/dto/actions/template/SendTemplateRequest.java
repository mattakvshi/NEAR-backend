package ru.mattakvshi.near.dto.actions.template;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SendTemplateRequest {

    @JsonSerialize(using = ToStringSerializer.class)
    private UUID templateId;

    @JsonSerialize(using = ToStringSerializer.class)
    private List<UUID> recipients;
}