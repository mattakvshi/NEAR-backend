package ru.mattakvshi.near.dto.notice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PushMessage extends NotificationMessage {
    private String deviceToken;
}
