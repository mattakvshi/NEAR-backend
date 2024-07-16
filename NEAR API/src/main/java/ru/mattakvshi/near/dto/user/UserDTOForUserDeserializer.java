//package ru.mattakvshi.near.dto.user;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.ObjectCodec;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
//import ru.mattakvshi.near.dto.community.UserDTOForCommunity;
//import ru.mattakvshi.near.dto.notice.NoticeTemplDTOForOwner;
//import ru.mattakvshi.near.entity.NotificationOptions;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//public class UserDTOForUserDeserializer extends StdDeserializer<UserDTOForUser> {
//    public UserDTOForUserDeserializer() {
//        super(UserDTOForUser.class);
//    }
//
//    @Override
//    public UserDTOForUser deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//        ObjectCodec codec = p.getCodec();
//        JsonNode node = codec.readTree(p);
//        UserDTOForUser dto = new UserDTOForUser();
//
//        dto.setId(UUID.fromString(node.get("id").asText()));
//        dto.setFirstName(node.get("firstName").asText());
//        dto.setLastName(node.get("lastName").asText());
//
//        if (node.hasNonNull("birthday")) {
//            dto.setBirthday(LocalDate.parse(node.get("birthday").asText()));
//        }
//
//        if (node.hasNonNull("age")) {
//            dto.setAge(node.get("age").asInt());
//        }
//
//        dto.setCountry(node.get("country").asText());
//        dto.setCity(node.get("city").asText());
//        dto.setDistrict(node.get("district").asText());
//
//        if (node.hasNonNull("registrationDate")) {
//            dto.setRegistrationDate(LocalDate.parse(node.get("registrationDate").asText()));
//        }
//
//        if (node.hasNonNull("selectedOptions")) {
//            List<NotificationOptions> selectedOptions = new ArrayList<>();
//            node.get("selectedOptions").forEach(optionNode -> {
//                NotificationOptions option = new NotificationOptions();
//                // заполняем опцию здесь, если у NotificationOptions есть параметры.
//                selectedOptions.add(option);
//            });
//            dto.setSelectedOptions(selectedOptions);
//        }
//
//        if (node.hasNonNull("friends")) {
//            List<UserDTOForCommunity> friends = new ArrayList<>();
//            node.get("friends").forEach(friendNode -> {
//                UserDTOForCommunity friend = new UserDTOForCommunity(); // инициализируйте друга
//                friends.add(friend);
//            });
//            dto.setFriends(friends);
//        }
//
//        if (node.hasNonNull("groups")) {
//            List<GroupDTOForUser> groups = new ArrayList<>();
//            node.get("groups").forEach(groupNode -> {
//                GroupDTOForUser group = new GroupDTOForUser(); // инициализируйте группу
//                groups.add(group);
//            });
//            dto.setGroups(groups);
//        }
//
//        if (node.hasNonNull("subscriptions")) {
//            List<CommunityDTOForUser> subscriptions = new ArrayList<>();
//            node.get("subscriptions").forEach(subscriptionNode -> {
//                CommunityDTOForUser subscription = new CommunityDTOForUser(); // инициализируйте подписку
//                subscriptions.add(subscription);
//            });
//            dto.setSubscriptions(subscriptions);
//        }
//
//        if (node.hasNonNull("notificationTemplates")) {
//            List<NoticeTemplDTOForOwner> notificationTemplates = new ArrayList<>();
//            node.get("notificationTemplates").forEach(notificationNode -> {
//                NoticeTemplDTOForOwner notification = new NoticeTemplDTOForOwner(); // инициализируйте шаблон уведомления
//                notificationTemplates.add(notification);
//            });
//            dto.setNotificationTemplates(notificationTemplates);
//        }
//
//        return dto;
//    }
//}
