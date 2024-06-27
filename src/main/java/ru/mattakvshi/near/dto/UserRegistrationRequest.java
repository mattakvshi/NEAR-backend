package ru.mattakvshi.near.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.mattakvshi.near.entity.NotificationOptions;
import ru.mattakvshi.near.entity.User;
import ru.mattakvshi.near.entity.auth.UserAccount;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserRegistrationRequest {

    @NotEmpty
    private String userName;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Length(min = 8)
    private String password;

    @NotEmpty
    @Pattern(regexp = "^[A-ZА-Я][a-zа-я\\-]+(\\s[A-ZА-Я][a-zа-я\\-]+)*,\\s[A-ZА-Я][a-zа-я\\-]+(\\s[A-ZА-Я][a-zа-я\\-]+)*,\\s[A-ZА-Я][a-zа-я\\-]+(\\s[A-ZА-Я][a-zа-я\\-]+)*$")
    private String location;

    @NotEmpty
    private LocalDate birthday;

    @NotEmpty
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
        user.setCity(splintLocation[2]);
        user.setDistrict(splintLocation[3]);
        user.setBirthday(birthday);
        user.setSelectedOptions(selectedOptions);

        //Связываем пользовательский аккаунт с пользователем
        userAccount.setUser(user);

        return userAccount;
    }
}

