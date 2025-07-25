package ru.mattakvshi.near.entity.auth;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

import java.util.UUID;

@Entity
@Table(name = "email_change_storage")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailChangeStorage {
    @Id
    private UUID token; // Уникальный токен для подтверждения


    @Column(name = "user_account_id", nullable = false)
    private UUID userAccountId; // Хранить только ID, а не всю сущность

    @Column(name = "new_email", nullable = false)
    private String newEmail; // Новый email, который хочет установить пользователь

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate; // Время истечения токена
}
