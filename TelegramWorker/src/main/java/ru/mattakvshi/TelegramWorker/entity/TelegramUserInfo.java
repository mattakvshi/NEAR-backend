package ru.mattakvshi.TelegramWorker.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_info")
@IdClass(TelegramUserInfoId.class)
public class TelegramUserInfo {

    @Id
    @Column(name = "user_name")
    private String userName;

    @Id
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "chat_id")
    private String chatId;
}
