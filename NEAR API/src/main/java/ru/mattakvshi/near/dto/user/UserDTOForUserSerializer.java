package ru.mattakvshi.near.dto.user;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.mattakvshi.near.dto.user.UserDTOForUser;

import java.io.IOException;

public class UserDTOForUserSerializer extends JsonSerializer<UserDTOForUser> {
    @Override
    public void serialize(UserDTOForUser value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", value.getId().toString());
        gen.writeStringField("firstName", value.getFirstName());
        gen.writeStringField("lastName", value.getLastName());
        gen.writeStringField("birthday", value.getBirthday() != null ? value.getBirthday().toString() : null);
        gen.writeNumberField("age", value.getAge() != null ? value.getAge() : 0);
        gen.writeStringField("country", value.getCountry());
        gen.writeStringField("city", value.getCity());
        gen.writeStringField("district", value.getDistrict());
        gen.writeStringField("registrationDate", value.getRegistrationDate() != null ? value.getRegistrationDate().toString() : null);
        gen.writeObjectField("selectedOptions", value.getSelectedOptions());
        gen.writeObjectField("friends", value.getFriends());
        gen.writeObjectField("groups", value.getGroups());
        gen.writeObjectField("subscriptions", value.getSubscriptions());
        gen.writeObjectField("notificationTemplates", value.getNotificationTemplates());
        gen.writeEndObject();
    }
}