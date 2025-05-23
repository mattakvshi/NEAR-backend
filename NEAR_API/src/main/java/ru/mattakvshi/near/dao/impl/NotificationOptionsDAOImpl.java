package ru.mattakvshi.near.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mattakvshi.near.dao.NotificationOptionsDAO;
import ru.mattakvshi.near.dao.repository.NotificationOptionsRepository;
import ru.mattakvshi.near.entity.NotificationOptions;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Component
public class NotificationOptionsDAOImpl implements NotificationOptionsDAO {

    @Autowired
    private NotificationOptionsRepository notificationOptionsRepository;

    @Override
    public List<NotificationOptions> findAllById(Iterable<Integer> ids) {
        return StreamSupport.stream(notificationOptionsRepository.findAllById(ids).spliterator(), false)
                .toList(); // Преобразуем Iterable в List
    }
}
