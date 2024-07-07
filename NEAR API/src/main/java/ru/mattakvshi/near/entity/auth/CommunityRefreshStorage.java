package ru.mattakvshi.near.entity.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "community_refresh_storage")
@AllArgsConstructor
@NoArgsConstructor
public class CommunityRefreshStorage {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "refresh_token")
    private String refreshToken;

}




