package ru.mattakvshi.near.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String country;
    private String city;
    private String district;

    @Email(message = "Некорректный email")
    private String email;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Некорректный номер телефона")
    private String phoneNumber;

    @Size(min = 5, max = 32, message = "Telegram short name must be between 5 and 32 characters")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{4,31}$", message = "Invalid Telegram short name")
    private String telegramShortName;

    private List<Integer> selectedOptions; // Для обновления опций уведомлений
}
