package ru.mattakvshi.TelegramWorker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class TelegramUserInfoId implements Serializable {
    private String userName;
    private String phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TelegramUserInfoId)) return false;
        TelegramUserInfoId that = (TelegramUserInfoId) o;
        return Objects.equals(getUserName(), that.getUserName()) &&
                Objects.equals(getPhoneNumber(), that.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName(), getPhoneNumber());
    }
}
