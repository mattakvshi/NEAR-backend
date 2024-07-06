package ru.mattakvshi.near.dto.notice;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelegramMessage extends NotificationMessage{
    private String phoneNumber;
}
