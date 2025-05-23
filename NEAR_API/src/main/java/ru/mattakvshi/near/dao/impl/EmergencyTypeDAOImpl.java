package ru.mattakvshi.near.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mattakvshi.near.dao.EmergencyTypeDAO;
import ru.mattakvshi.near.dao.repository.EmergencyTypeRepository;
import ru.mattakvshi.near.entity.EmergencyTypes;

import java.util.List;
import java.util.stream.StreamSupport;

@Component
public class EmergencyTypeDAOImpl implements EmergencyTypeDAO {

    @Autowired
    EmergencyTypeRepository emergencyTypeRepository;

    @Override
    public List<EmergencyTypes> findAllById(Iterable<Integer> ids) {
        return StreamSupport.stream(emergencyTypeRepository.findAllById(ids).spliterator(), false)
                .toList();
    }
}
