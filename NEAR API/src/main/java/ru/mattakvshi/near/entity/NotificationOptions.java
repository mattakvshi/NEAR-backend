package ru.mattakvshi.near.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Entity
@Table(name = "Notification_options")
public class NotificationOptions implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "options_id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "color")
    private String color;

    @Column(name = "bg_color")
    private String bgColor;


    @Column(name = "color_dark")
    private String colorDark;


    @Column(name = "bg_color_dark")
    private String bgColorDark;

    @Override
    public String toString() {
        return "NotificationOptions{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", color='" + color + '\'' +
                ", bgColor='" + bgColor + '\'' +
                ", colorDark='" + colorDark + '\'' +
                ", bgColorDark='" + bgColorDark + '\'' +
                '}';
    }
}
