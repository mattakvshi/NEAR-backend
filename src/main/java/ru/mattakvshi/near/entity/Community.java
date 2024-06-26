package ru.mattakvshi.near.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "Communities")
public class Community {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(
            name = "community_id",
            updatable = false,
            nullable = false
    )
    @JsonSerialize(using = ToStringSerializer.class)
    private UUID id;

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

    @ManyToMany(mappedBy = "subscriptions")
    private List<User> subscribers;

    @ManyToMany
    @JoinTable(
            name = "Monitored_emergency",
            joinColumns = @JoinColumn(name = "community_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private List<EmergencyTypes> monitoredEmergencyTypes;

}
