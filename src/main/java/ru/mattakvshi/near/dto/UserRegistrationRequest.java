package ru.mattakvshi.near.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.mattakvshi.near.entity.auth.UserAccount;

@Data
public class UserRegistrationRequest {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Length(min = 8)
    private String password;

    public UserAccount toAccount() {
        UserAccount userAccount = new UserAccount();
        userAccount.setEmail(this.email);
        userAccount.setPassword(this.password);
        return userAccount;
    }
}

