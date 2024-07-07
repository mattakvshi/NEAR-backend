package ru.mattakvshi.TelegramWorker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelegramMessage extends NotificationMessage{
    private String phoneNumber;
}
