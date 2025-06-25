package ru.mattakvshi.near.dto.auth;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeEmailRequest {
    private String newEmail;
}
