package ru.mattakvshi.near.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mattakvshi.near.entity.NotificationTemplate;

import java.util.UUID;

@Repository
public interface NotificationTemplateRepository extends CrudRepository<NotificationTemplate, UUID> {
}
