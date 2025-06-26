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

@Data
@Entity
@Table(name = "email_verification_token_community")
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerificationTokenCommunity {
    @Id
    private UUID token;

    @Column(name = "community_account_id", nullable = false)
    private UUID communityAccountId; // Хранить только ID, а не всю сущность

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate; // Например, 24 часа
}
