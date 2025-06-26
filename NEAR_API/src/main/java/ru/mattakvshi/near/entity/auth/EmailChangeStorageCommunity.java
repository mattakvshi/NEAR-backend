package ru.mattakvshi.near.entity.auth;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "email_change_storage_community")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailChangeStorageCommunity {
    @Id
    private UUID token; // Уникальный токен для подтверждения

    @Column(name = "community_account_id", nullable = false)
    private UUID communityAccountId; // Хранить только ID, а не всю сущность

    @Column(name = "new_email", nullable = false)
    private String newEmail; // Новый email, который хочет установить пользователь

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate; // Время истечения токена
}
