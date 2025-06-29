package ru.mattakvshi.near.dto.user;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import ru.mattakvshi.near.dto.notice.NoticeTemplDTOForOwner;
import ru.mattakvshi.near.dto.community.UserDTOForCommunity;
import ru.mattakvshi.near.entity.*;
import ru.mattakvshi.near.entity.base.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class UserDTOForUser implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private UUID id;

    private String userAvatar;

    private String firstName;

    private String lastName;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthday;

    private Integer age;

    private String country;

    private String city;

    private String district;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate registrationDate;

    private String deviceToken;

    //@OneToMany(fetch = FetchType.EAGER)
    //private List<NotificationOptions> selectedOptions;

    private List<UserDTOForCommunity> friends;

    private List<GroupDTOForUser> groups;

    private List<CommunityDTOForUser> subscriptions;

    private List<NoticeTemplDTOForOwner> notificationTemplates;

    public static UserDTOForUser from(User user) {
        UserDTOForUser dto = new UserDTOForUser();
        dto.setId(user.getId());
        dto.setUserAvatar(user.getUserAvatar());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setBirthday(user.getBirthday());
        dto.setAge(user.getAge());
        dto.setCountry(user.getCountry());
        dto.setCity(user.getCity());
        dto.setDistrict(user.getDistrict());
        dto.setRegistrationDate(user.getRegistrationDate());
        dto.setDeviceToken(user.getDeviceToken());

        //dto.setSelectedOptions(user.getSelectedOptions());

        dto.setFriends(
                user.getFriends()
                .stream()
                .map(UserDTOForCommunity::from)
                .collect(Collectors.toList())
        );

        dto.setGroups(
                user.getGroups()
                        .stream()
                        .map(GroupDTOForUser::from)
                        .collect(Collectors.toList())
        );

        dto.setSubscriptions(
                user.getSubscriptions()
                .stream()
                .map(CommunityDTOForUser::from)
                .collect(Collectors.toList())
        );

        dto.setNotificationTemplates(
                user.getNotificationTemplates()
                .stream()
                .map(NoticeTemplDTOForOwner::from)
                .collect(Collectors.toList())
        );

        return dto;
    }

    public static List<UserDTOForUser> fromList(List<User> users) {
        return users.stream()
                .map(UserDTOForUser::from)
                .collect(Collectors.toList());
    }
}