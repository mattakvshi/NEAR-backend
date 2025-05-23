package ru.mattakvshi.near.dao.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mattakvshi.near.entity.EmergencyTypes;

@Repository
public interface EmergencyTypeRepository extends CrudRepository<EmergencyTypes, Integer> {
}
