package ru.mattakvshi.near.dto.actions.template;

import lombok.Getter;
import ru.mattakvshi.near.entity.EmergencyTypes;

import java.util.UUID;

@Getter
public class TemplateFindRequest {

    private UUID templateId;

    private String templateName;

    private String message;

    private EmergencyTypes emergencyType;
}
