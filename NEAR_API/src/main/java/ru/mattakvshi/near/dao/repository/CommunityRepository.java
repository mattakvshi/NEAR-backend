package ru.mattakvshi.near.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mattakvshi.near.entity.base.Community;

import java.awt.print.Pageable;
import java.util.UUID;

@Repository
public interface CommunityRepository extends CrudRepository<Community, UUID> {
    Page<Community> findAllByCommunityNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String nameQuery, String descriptionQuery, Pageable pageable
    );
}
