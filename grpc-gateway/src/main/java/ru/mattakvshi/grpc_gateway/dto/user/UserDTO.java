package ru.mattakvshi.grpc_gateway.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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
                .append("Имя: ").append(firstName != null ? firstName : "не указано").append("\n")
                .append("Фамилия: ").append(lastName != null ? lastName : "не указано").append("\n")
                .append("Дата рождения: ").append(birthday != null ? birthday : "не указано").append("\n")
                .append("Возраст: ").append(age != null ? age : "не указано").append("\n")
                .append("Страна: ").append(country != null ? country : "не указано").append("\n")
                .append("Город: ").append(city != null ? city : "не указано").append("\n")
                .append("Район: ").append(district != null ? district : "не указано").append("\n")
                .append("Дата регистрации: ").append(registrationDate != null ? registrationDate : "не указано").append("\n")
                .append("__________________________\n");

        sb.append("Выбранные опции уведомлений:\n");
        if (selectedOptions != null && !selectedOptions.isEmpty()) {
            sb.append(selectedOptions.stream()
                    .filter(Objects::nonNull)
                    .map(NotificationOptions::getTitle)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(",\n")));
        } else {
            sb.append("не выбраны");
        }
        sb.append("\n").append("__________________________\n");

        sb.append("Друзья:\n");
        if (friends != null && !friends.isEmpty()) {
            sb.append(friends.stream()
                    .filter(Objects::nonNull)
                    .map(friend -> (friend.getFirstName() != null ? friend.getFirstName() : "") + " "
                            + (friend.getLastName() != null ? friend.getLastName() : ""))
                    .filter(name -> !name.trim().isEmpty())
                    .collect(Collectors.joining(",\n")));
        } else {
            sb.append("отсутствуют");
        }
        sb.append("\n").append("__________________________\n");

        sb.append("Группы:\n");
        if (groups != null && !groups.isEmpty()) {
            sb.append(groups.stream()
                    .filter(Objects::nonNull)
                    .map(GroupDTOSmall::getGroupName)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(",\n")));
        } else {
            sb.append("отсутствуют");
        }
        sb.append("\n").append("__________________________\n");

        sb.append("Подписки:\n");
        if (subscriptions != null && !subscriptions.isEmpty()) {
            sb.append(subscriptions.stream()
                    .filter(Objects::nonNull)
                    .map(CommunityDTOSmall::getCommunityName)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(",\n")));
        } else {
            sb.append("отсутствуют");
        }
        sb.append("\n").append("__________________________\n");

        sb.append("Шаблоны уведомлений:\n");
        if (notificationTemplates != null && !notificationTemplates.isEmpty()) {
            sb.append(notificationTemplates.stream()
                    .filter(Objects::nonNull)
                    .map(NotificationTemplateDTO::getTemplateName)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(",\n")));
        } else {
            sb.append("отсутствуют");
        }

        return sb.toString();
    }

}

