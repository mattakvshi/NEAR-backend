package ru.mattakvshi.near.dto.community;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityUpdateRequest {
    private String communityName;
    private String description;
    private String country;
    private String city;
    private String district;
    private List<Integer> emergencyTypeIds; // Для обновления monitoredEmergencyTypes
}