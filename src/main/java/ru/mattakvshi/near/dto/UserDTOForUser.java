package ru.mattakvshi.near.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import ru.mattakvshi.near.entity.*;
import ru.mattakvshi.near.entity.base.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class UserDTOForUser {

    @JsonSerialize(using = ToStringSerializer.class)
    private UUID id;

    private String firstName;

    private String lastName;

    private LocalDate birthday;

    private Integer age;

    private String country;

    private String city;

    private String district;

    private LocalDate registrationDate;

    private List<NotificationOptions> selectedOptions;

    private List<UserDTOForCommunity> friends;

    private List<Group> groups;

    private List<CommunityDTOForUser> subscriptions;

    private List<NotificationTemplate> notificationTemplates;

    public static UserDTOForUser from(User user) {
        UserDTOForUser dto = new UserDTOForUser();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setBirthday(user.getBirthday());
        dto.setAge(user.getAge());
        dto.setCountry(user.getCountry());
        dto.setCity(user.getCity());
        dto.setDistrict(user.getDistrict());
        dto.setRegistrationDate(user.getRegistrationDate());
        dto.setSelectedOptions(user.getSelectedOptions());

        dto.setFriends(
                user.getFriends()
                .stream()
                .map(UserDTOForCommunity::from)
                .collect(Collectors.toList())
        );

        dto.setGroups(user.getGroups());

        dto.setSubscriptions(
                user.getSubscriptions()
                .stream()
                .map(CommunityDTOForUser::from)
                .collect(Collectors.toList())
        );

        dto.setNotificationTemplates(user.getNotificationTemplates());

        return dto;
    }
}