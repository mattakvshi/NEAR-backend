package ru.mattakvshi.near.dao;

import ru.mattakvshi.near.entity.EmergencyTypes;

import java.util.List;


public interface EmergencyTypeDAO {
    List<EmergencyTypes> findAllById(Iterable<Integer> ids);

}
