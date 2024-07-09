package ru.mattakvshi.near.entity.base;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import ru.mattakvshi.near.entity.NotificationTemplate;

import java.util.List;
import java.util.UUID;

@Data
//@MappedSuperclass - Сначала планировал, что этот класс не будет сущьностью, а только обобщением двух других в рамках логики
@Table(name = "Owners_data")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class TemplateOwner {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(
            name = "id",
            updatable = false,
            nullable = false
    )
    @JsonSerialize(using = ToStringSerializer.class)
    private UUID id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    private List<NotificationTemplate> notificationTemplates;

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

//Аннотация @Inheritance(strategy = InheritanceType.JOINED) в Java Persistence API (JPA) используется для указания стратегии
// наследования между сущностями в иерархии классов. В контексте JPA, стратегия JOINED означает, что каждый класс в иерархии наследования
// будет соответствовать отдельной таблице в базе данных.
//
//Вот как это работает:
//
//Для каждого класса в иерархии создается отдельная таблица.
//Таблицы связаны между собой через внешние ключи, которые соответствуют первичным ключам родительских таблиц.
//Когда происходит запрос к дочернему классу, JPA выполняет соединение (JOIN) таблиц родителя и потомка,
// чтобы собрать все поля, относящиеся к дочернему классу.
//Эта стратегия часто используется, когда в дочерних классах есть уникальные атрибуты, которые не присутствуют в родительском классе,
// и когда необходимо оптимизировать производительность за счет уменьшения количества ненужных столбцов в таблицах.
//