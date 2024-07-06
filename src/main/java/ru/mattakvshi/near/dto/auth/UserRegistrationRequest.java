package ru.mattakvshi.near.dto.auth;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;
import ru.mattakvshi.near.entity.NotificationOptions;
import ru.mattakvshi.near.entity.base.User;
import ru.mattakvshi.near.entity.auth.UserAccount;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserRegistrationRequest {

    //@NotEmpty - @NotBlank знал что эта аннотация строже
    @NotBlank
    private String userName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 8)
    private String password;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number")
    private String phoneNumber;

    @Size(min = 5, max = 32, message = "Telegram short name must be between 5 and 32 characters")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{4,31}$", message = "Invalid Telegram short name")
    private String telegramShortName;

    @NotBlank
    @Pattern(regexp = "^[A-ZА-Я][a-zа-я\\-]*(\\s[a-zа-яA-ZА-Я\\-]*)*\\s*,\\s*[A-ZА-Я][a-zа-я\\-]*(\\s[a-zа-яA-ZА-Я\\-]*)*\\s*,\\s*[A-ZА-Я][a-zа-я\\-]*(\\s[a-zа-яA-ZА-Я\\-]*)*$")
    private String location;

    @NotNull
    private LocalDate birthday;

    @NotBlank
    private List<NotificationOptions> selectedOptions;


    public UserAccount toAccount() {
        //Создаём сущность пользовательского аккаунта из распаршенного Json
        UserAccount userAccount = new UserAccount();
        userAccount.setEmail(this.email);
        userAccount.setPassword(this.password);

        //Создаём сущность User из первичного набора данных
        User user = new User();
        user.setId(userAccount.getId());
        String[] splintName = this.userName.split(" ");
        user.setFirstName(splintName[0]);
        user.setLastName(splintName[1]);
        String[] splintLocation = this.location.split(", ");
        user.setCountry(splintLocation[0]);
        user.setCity(splintLocation[1]);
        user.setDistrict(splintLocation[2]);
        user.setBirthday(birthday);

        user.setEmail(this.email);
        user.setPhoneNumber(this.phoneNumber);
        user.setTelegramShortName(this.telegramShortName);

        user.setSelectedOptions(selectedOptions);

        //Связываем пользовательский аккаунт с пользователем
        userAccount.setUser(user);

        return userAccount;
    }
}

