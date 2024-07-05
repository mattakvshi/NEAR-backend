package ru.mattakvshi.near.dto.community;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import ru.mattakvshi.near.entity.NotificationOptions;
import ru.mattakvshi.near.entity.base.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class UserDTOForCommunity {

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

    private int friendsCount;

    //private int groupsCount;

    private int subscriptionsCount;

    //private int notificationTemplatesCount;

    public static UserDTOForCommunity from(User user) {
        UserDTOForCommunity dto = new UserDTOForCommunity();
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

        dto.setFriendsCount(user.getFriends() != null ? user.getFriends().size() : 0);
        //dto.setGroupsCount(user.getGroups() != null ? user.getGroups().size() : 0);

        dto.setSubscriptionsCount(user.getSubscriptions() != null ? user.getSubscriptions().size() : 0);

        //dto.setNotificationTemplatesCount(user.getNotificationTemplates() != null ? user.getNotificationTemplates().size() : 0);

        return dto;
    }
}
