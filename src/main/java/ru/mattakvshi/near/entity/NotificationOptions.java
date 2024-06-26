package ru.mattakvshi.near.entity;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "Notification_options")
public class NotificationOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "options_id")
    private int id;

    @Column(name = "option")
    private String notificationOption;
}
