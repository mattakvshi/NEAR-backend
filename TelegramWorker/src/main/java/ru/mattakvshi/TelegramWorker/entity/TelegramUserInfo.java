package ru.mattakvshi.TelegramWorker.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_info")
public class TelegramUserInfo {

    @EmbeddedId
    private TelegramUserInfoId id;

    @Column(name = "chat_id")
    private String chatId;
}
