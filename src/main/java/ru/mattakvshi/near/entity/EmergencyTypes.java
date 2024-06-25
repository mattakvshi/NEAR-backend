package ru.mattakvshi.near.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Emergency_type")
public class EmergencyTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private int id;

    @Column(name = "type")
    private String type;

}
