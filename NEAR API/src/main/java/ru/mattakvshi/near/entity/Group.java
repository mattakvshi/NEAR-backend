package ru.mattakvshi.near.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import ru.mattakvshi.near.entity.base.User;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name ="Groups")
public class Group implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(
            name = "group_id",
            updatable = false,
            nullable = false
    )
    @JsonSerialize(using = ToStringSerializer.class)
    private UUID id;

    @Column(name = "group_name")
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "groups_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members;
}