package ru.mattakvshi.near.entity;


import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

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
