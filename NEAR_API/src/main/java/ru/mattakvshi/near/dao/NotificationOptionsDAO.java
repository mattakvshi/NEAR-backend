package ru.mattakvshi.near.dao;

import ru.mattakvshi.near.entity.NotificationOptions;

import java.util.List;


public interface NotificationOptionsDAO {

    List<NotificationOptions> findAllById(Iterable<Integer> is);
}
