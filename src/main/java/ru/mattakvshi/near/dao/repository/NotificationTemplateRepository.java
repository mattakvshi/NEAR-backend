package ru.mattakvshi.near.dao.repository;

import org.springframework.data.repository.CrudRepository;
import ru.mattakvshi.near.entity.NotificationTemplate;

import java.util.UUID;

public interface NotificationTemplateRepository extends CrudRepository<NotificationTemplate, UUID> {
}
