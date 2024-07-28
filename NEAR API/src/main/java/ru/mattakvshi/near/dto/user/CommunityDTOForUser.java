package ru.mattakvshi.near.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;
import ru.mattakvshi.near.entity.base.Community;
import ru.mattakvshi.near.entity.EmergencyTypes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class CommunityDTOForUser implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private UUID id;

    private String communityName;

    private String description;

    private String country;

    private String city;

    private String district;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate registrationDate;

    private int subscribersCount;

    //private List<EmergencyTypes> monitoredEmergencyTypesCount;

    public static CommunityDTOForUser from(Community community) {
        CommunityDTOForUser dto = new CommunityDTOForUser();
        dto.setId(community.getId());
        dto.setCommunityName(community.getCommunityName());
        dto.setDescription(community.getDescription());
        dto.setCountry(community.getCountry());
        dto.setCity(community.getCity());
        dto.setDistrict(community.getDistrict());
        dto.setRegistrationDate(community.getRegistrationDate());
        dto.setSubscribersCount(community.getSubscribers() != null ? community.getSubscribers().size() : 0);
        //dto.setMonitoredEmergencyTypesCount(community.getMonitoredEmergencyTypes());

        return dto;
    }
}