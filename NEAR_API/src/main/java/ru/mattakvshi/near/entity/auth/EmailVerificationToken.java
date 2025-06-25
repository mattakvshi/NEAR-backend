package ru.mattakvshi.near.entity.auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "email_verification_token")
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerificationToken {
    @Id
    private UUID token;

    @Column(name = "user_account_id", nullable = false)
    private UUID userAccountId; // Хранить только ID, а не всю сущность

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate; // Например, 24 часа
}
