package ru.mattakvshi.near.entity.base;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import ru.mattakvshi.near.dto.user.UserDTOForUser;
import ru.mattakvshi.near.entity.Group;
import ru.mattakvshi.near.entity.NotificationOptions;
import ru.mattakvshi.near.entity.NotificationTemplate;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name ="Users")
public class User extends TemplateOwner implements Serializable {

    //Вынес id в супер класс, так как нужны уникальные на уровне двух сущностей, для работы с шаблонами

    @Column(name = "user_avatar")
    private String userAvatar;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "age")
    private Integer age;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "device_token")
    private String deviceToken;

    @Column(name = "telegram_short_name")
    private String telegramShortName;

    @CreationTimestamp //Заполняется первый раз при регистрации
    @Column(name = "registration_date", updatable = false) //и после не изменяется никогда
    private LocalDate registrationDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Selected_options",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "options_id")
    )
    private List<NotificationOptions> selectedOptions;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "User_Friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<User> friends;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "friend_requests_sent",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "receiver_id")
    )
    private List<User> sentRequests;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "friend_requests_received",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "sender_id")
    )
    private List<User> receivedRequests;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "owner")
    private List<Group> groups;

//    """
//    Атрибут mappedBy используется в аннотации @OneToMany для указания,
//    какое поле в дочерней сущности управляет отношением с родительской сущностью.
//    Это атрибут указывает имя свойства в дочерней сущности,
//    которое устанавливает связь с родительской сущностью.
//    """

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "subscribers_subscriptions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "community_id")
    )
    private List<Community> subscriptions;

    @PrePersist //Вызываем метод перед сохранением сущности в базу
    @PreUpdate //Вызываем метод перед обновлением сущности
    public void calculateAge() {
        if (birthday != null) {
            this.age = Period.between(birthday, LocalDate.now()).getYears();
        } else {
            this.age = null;
        }
    }

    @Override
    public String toString() {
        return UserDTOForUser.from(this).toString();
    }


}
