package ru.mattakvshi.near.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name ="Users")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(
            name = "user_id",
            updatable = false,
            nullable = false
    )
    @JsonSerialize(using = ToStringSerializer.class)
    private UUID id;

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

    @CreationTimestamp //Заполняется первый раз при регестрации
    @Column(name = "registration_date", updatable = false) //и после не изменяется никогда
    private LocalDate registrationDate;

    @ManyToMany
    @JoinTable(
            name = "Selected_options",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "options_id")
    )
    private List<NotificationOptions> selectedOptions;


    @OneToMany(mappedBy = "owner")
    private List<Group> groups;

//    """
//    Атрибут mappedBy используется в аннотации @OneToMany для указания,
//    какое поле в дочерней сущности управляет отношением с родительской сущностью.
//    Это атрибут указывает имя свойства в дочерней сущности,
//    которое устанавливает связь с родительской сущностью.
//    """

    @ManyToMany
    @JoinTable(
            name = "subscriptions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "community_id")
    )
    private List<Community> subscriptions;

    @OneToMany(mappedBy = "owner")
    private List<NotificationTemplate> notificationTemplates;

    @PrePersist //Вызываем метод перед сохранением сущьности в базу
    @PreUpdate //Вызываем метод перед обновлением сущьности
    public void calculateAge() {
        if (birthday != null) {
            this.age = Period.between(birthday, LocalDate.now()).getYears();
        } else {
            this.age = null;
        }
    }


}
