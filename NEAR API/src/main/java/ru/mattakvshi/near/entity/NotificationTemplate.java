package ru.mattakvshi.near.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import ru.mattakvshi.near.entity.base.TemplateOwner;

import java.util.UUID;

@Data
@Entity
@Table(name ="Notification_templates")
@AllArgsConstructor
@NoArgsConstructor
public class NotificationTemplate {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(
            name = "template_id",
            updatable = false,
            nullable = false
    )
    @JsonSerialize(using = ToStringSerializer.class)
    private UUID id;


    @Column(name = "template_name")
    private String templateName;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "owner_id")
    private TemplateOwner owner;

    @Column(name = "notification_message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "emergency_type_id", referencedColumnName = "type_id")
    private EmergencyTypes emergencyType;

}
