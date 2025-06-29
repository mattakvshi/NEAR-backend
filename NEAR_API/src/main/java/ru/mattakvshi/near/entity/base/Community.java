package ru.mattakvshi.near.entity.base;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import ru.mattakvshi.near.entity.EmergencyTypes;
import ru.mattakvshi.near.entity.NotificationTemplate;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "Communities")
public class Community extends TemplateOwner implements Serializable {

    //Вынес id в супер класс, так как нужны уникальные на уровне двух сущностей, для работы с шаблонами

    @Column(name = "community_avatar")
    private String communityAvatar;

    @Column(name = "community_name")
    private String communityName;

    @Column(name = "description")
    private String description;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @CreationTimestamp //Заполняется первый раз при регистрации
    @Column(name = "registration_date", updatable = false) //и после не изменяется никогда
    private LocalDate registrationDate;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "subscriptions")
    private List<User> subscribers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Monitored_emergency",
            joinColumns = @JoinColumn(name = "community_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private List<EmergencyTypes> monitoredEmergencyTypes;
}
