package ru.mattakvshi.grpc_gateway.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class CommunityDTOSmall {

    @JsonSerialize(using = ToStringSerializer.class)
    private UUID id;

    private String communityName;

    private String description;

    private String country;

    private String city;

    private String district;

    private LocalDate registrationDate;

    private int subscribersCount;

    private List<EmergencyTypes> monitoredEmergencyTypesCount;

}