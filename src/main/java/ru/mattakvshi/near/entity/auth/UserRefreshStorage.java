package ru.mattakvshi.near.entity.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "user_refresh_storage")
@AllArgsConstructor
@NoArgsConstructor
public class UserRefreshStorage {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "refresh_token")
    private String refreshToken;

}
