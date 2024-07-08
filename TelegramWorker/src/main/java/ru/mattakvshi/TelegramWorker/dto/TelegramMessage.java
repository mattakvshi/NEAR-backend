package ru.mattakvshi.TelegramWorker.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class TelegramMessage extends NotificationMessage{
    private String phoneNumber;

    @Override
    public String toString() {
        return super.toString() + " " +
                "TelegramMessage [phoneNumber=" + phoneNumber + "]";
    }
}
