package ru.mattakvshi.near.entity.base;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@MappedSuperclass
public abstract class TemplateOwner {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(
            name = "owner_id",
            updatable = false,
            nullable = false
    )
    @JsonSerialize(using = ToStringSerializer.class)
    private UUID templateOwnerId;

}

//Аннотация @MappedSuperclass в Java Persistence API (JPA) используется для указания, что класс является суперклассом и
// не должен быть связан с отдельной таблицей в базе данных. Вместо этого, его поля и методы наследуются дочерними сущностями,
// которые являются JPA-сущностями и связаны с конкретными таблицами.
//
//Вот основные моменты использования @MappedSuperclass:
//
//Наследование: Классы, помеченные как @MappedSuperclass, не могут быть использованы напрямую для создания сущностей.
// Они предназначены для наследования и предоставления общих полей и методов своим подклассам.
//Нет соответствующей таблицы: Для @MappedSuperclass не создается отдельная таблица в базе данных.
// Все аннотированные поля и методы наследуются дочерними сущностями, и каждая из этих сущностей будет иметь свою таблицу с этими полями.
//Переиспользование кода: Это позволяет избежать дублирования кода, предоставляя общую структуру для различных сущностей.
//Пример использования @MappedSuperclass вы уже видели в предыдущем сообщении, где Owner является абстрактным суперклассом
// с общим owner_id, который наследуется классами User и Community.
//
//Это очень полезно в ситуациях, когда у вас есть общие атрибуты или поведение, которые вы хотите включить в несколько сущностей
// в вашем приложении. Использование @MappedSuperclass облегчает поддержку и расширение кода, так как изменения
// в общих полях или методах нужно вносить только в одном месте.
