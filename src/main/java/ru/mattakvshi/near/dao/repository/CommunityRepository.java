package ru.mattakvshi.near.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mattakvshi.near.entity.Community;

import java.util.UUID;

@Repository
public interface CommunityRepository extends CrudRepository<Community, UUID> {
}
