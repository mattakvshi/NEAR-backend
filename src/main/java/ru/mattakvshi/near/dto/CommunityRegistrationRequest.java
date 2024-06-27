package ru.mattakvshi.near.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.mattakvshi.near.entity.auth.CommunityAccount;

@Data
public class CommunityRegistrationRequest {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Length(min = 8)
    private String password;

    public CommunityAccount toAccount() {
        CommunityAccount communityAccount = new CommunityAccount();
        communityAccount.setEmail(this.email);
        communityAccount.setPassword(this.password);
        return communityAccount;
    }

}
