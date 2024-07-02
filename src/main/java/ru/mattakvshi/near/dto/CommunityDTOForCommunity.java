package ru.mattakvshi.near.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import ru.mattakvshi.near.entity.Community;
import ru.mattakvshi.near.entity.EmergencyTypes;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class CommunityDTOForCommunity {

    @JsonSerialize(using = ToStringSerializer.class)
    private UUID id;

    private String communityName;

    private String description;

    private String country;

    private String city;

    private String district;

    private LocalDate registrationDate;

    private List<UserDTOForCommunity> subscribers;

    private List<EmergencyTypes> monitoredEmergencyTypesCount;

    public static CommunityDTOForCommunity from(Community community) {
        CommunityDTOForCommunity dto = new CommunityDTOForCommunity();
        dto.setId(community.getId());
        dto.setCommunityName(community.getCommunityName());
        dto.setDescription(community.getDescription());
        dto.setCountry(community.getCountry());
        dto.setCity(community.getCity());
        dto.setDistrict(community.getDistrict());
        dto.setRegistrationDate(community.getRegistrationDate());

        dto.setSubscribers(
                community.getSubscribers()
                        .stream()
                        .map(UserDTOForCommunity::from)
                        .collect(Collectors.toList())
        );

        dto.setMonitoredEmergencyTypesCount(community.getMonitoredEmergencyTypes());

        return dto;
    }
}
