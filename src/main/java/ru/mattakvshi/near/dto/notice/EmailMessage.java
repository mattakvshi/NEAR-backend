package ru.mattakvshi.near.dto.notice;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailMessage extends NotificationMessage{
    private String email;
}
