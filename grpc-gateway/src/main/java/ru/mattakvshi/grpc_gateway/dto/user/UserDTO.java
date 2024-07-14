package ru.mattakvshi.grpc_gateway.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class UserDTO {

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

    private List<UserDTOSmall> friends;

    private List<GroupDTOSmall> groups;

    private List<CommunityDTOSmall> subscriptions;

    private List<NotificationTemplateDTO> notificationTemplates;

//    @Override
//    public String toString() {
//        return "Информация о пользователе:\n" +
//                "__________________________\n" +
//                "Имя: " + firstName + "\n" +
//                "Фамилия: " + lastName + "\n" +
//                "Дата рождения: " + birthday + "\n" +
//                "Возраст: " + age + "\n" +
//                "Страна: " + country + "\n" +
//                "Город: " + city + "\n" +
//                "Район: " + district + "\n" +
//                "Дата регистрации: " + registrationDate + "\n" +
//                "__________________________\n" +
//                "Выбранные опции уведомлений:\n" + selectedOptions.stream()
//                .map(NotificationOptions::getTitle)
//                .collect(Collectors.joining(",\n")) + "\n" +
//                "__________________________\n" +
//                "Друзья:\n" + friends.stream()
//                .map(friend -> friend.getFirstName() + " " + friend.getLastName())
//                .collect(Collectors.joining(",\n")) + "\n" +
//                "__________________________\n" +
//                "Группы:\n" + groups.stream()
//                .map(GroupDTOSmall::getGroupName)
//                .collect(Collectors.joining(",\n")) + "\n" +
//                "__________________________\n" +
//                "Подписки:\n" + subscriptions.stream()
//                .map(CommunityDTOSmall::getCommunityName)
//                .collect(Collectors.joining(",\n")) + "\n" +
//                "__________________________\n" +
//                "Шаблоны уведомлений:\n" + notificationTemplates.stream()
//                .map(NotificationTemplateDTO::getTemplateName)
//                .collect(Collectors.joining(",\n"));
//    }

    //Оптимизировал, прикиньте, что-то ещё помню

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Информация о пользователе:\n")
                .append("__________________________\n")
                .append("Имя: ").append(firstName).append("\n")
                .append("Фамилия: ").append(lastName).append("\n")
                .append("Дата рождения: ").append(birthday).append("\n")
                .append("Возраст: ").append(age).append("\n")
                .append("Страна: ").append(country).append("\n")
                .append("Город: ").append(city).append("\n")
                .append("Район: ").append(district).append("\n")
                .append("Дата регистрации: ").append(registrationDate).append("\n")
                .append("__________________________\n")
                .append("Выбранные опции уведомлений:\n")
                .append(selectedOptions.stream()
                        .map(NotificationOptions::getTitle)
                        .collect(Collectors.joining(",\n")))
                .append("\n")
                .append("__________________________\n")
                .append("Друзья:\n")
                .append(friends.stream()
                        .map(friend -> friend.getFirstName() + " " + friend.getLastName())
                        .collect(Collectors.joining(",\n")))
                .append("\n")
                .append("__________________________\n")
                .append("Группы:\n")
                .append(groups.stream()
                        .map(GroupDTOSmall::getGroupName)
                        .collect(Collectors.joining(",\n")))
                .append("\n")
                .append("__________________________\n")
                .append("Подписки:\n")
                .append(subscriptions.stream()
                        .map(CommunityDTOSmall::getCommunityName)
                        .collect(Collectors.joining(",\n")))
                .append("\n")
                .append("__________________________\n")
                .append("Шаблоны уведомлений:\n")
                .append(notificationTemplates.stream()
                        .map(NotificationTemplateDTO::getTemplateName)
                        .collect(Collectors.joining(",\n")));
        return sb.toString();
    }

}

