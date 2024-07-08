package ru.mattakvshi.MobilePushWorker.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter

public class PushMessage extends NotificationMessage {
    private String deviceToken;

    @Override
    public String toString() {
        return super.toString() + " " +
                "PushMessage [deviceToken=" + deviceToken + "]";
    }
}
