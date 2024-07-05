package ru.mattakvshi.near.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.mattakvshi.near.entity.base.Community;
import ru.mattakvshi.near.entity.EmergencyTypes;
import ru.mattakvshi.near.entity.auth.CommunityAccount;

import java.util.List;

@Data
public class CommunityRegistrationRequest {

    @NotEmpty
    private String communityName;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Length(min = 8)
    private String password;

    @NotEmpty
    @Pattern(regexp = "^[A-ZА-Я][a-zа-я\\-]*((\\s*,\\s*[A-ZА-Я][a-zа-я\\-]*(\\s[a-zа-яA-ZА-Я\\-]*)*)*)*$")
    private String location;

    @NotEmpty
    private List<EmergencyTypes> monitoredEmergencyTypes;

    public CommunityAccount toAccount() {
        //Создаём сущность аккаунта сообщества из распаршенного Json
        CommunityAccount communityAccount = new CommunityAccount();
        communityAccount.setEmail(this.email);
        communityAccount.setPassword(this.password);

        //Создаём сущность Community из первичного набора данных
        Community community = new Community();
        community.setId(communityAccount.getId());
        community.setCommunityName(this.communityName);
        String[] splintLocation = this.location.split(", ");
        switch (splintLocation.length){
            case 1 -> community.setCountry(splintLocation[0]);
            case 2 -> {
                community.setCountry(splintLocation[0]);
                community.setCity(splintLocation[1]);
            }
            case 3 -> {
                community.setCountry(splintLocation[0]);
                community.setCity(splintLocation[1]);
                community.setDistrict(splintLocation[2]);
            }

        }
        community.setMonitoredEmergencyTypes(monitoredEmergencyTypes);

        //Связываем аккаунт сообщества с сообществом
        communityAccount.setCommunity(community);

        return communityAccount;
    }

}
