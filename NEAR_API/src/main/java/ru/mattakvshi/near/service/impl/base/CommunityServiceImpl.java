package ru.mattakvshi.near.service.impl.base;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dao.CommunityDAO;
import ru.mattakvshi.near.dao.EmergencyTypeDAO;
import ru.mattakvshi.near.dto.community.CommunityDTOForCommunity;
import ru.mattakvshi.near.dto.community.CommunityUpdateRequest;
import ru.mattakvshi.near.dto.user.UserDTOForUser;
import ru.mattakvshi.near.entity.EmergencyTypes;
import ru.mattakvshi.near.entity.base.Community;
import ru.mattakvshi.near.service.CommunityService;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommunityServiceImpl  implements CommunityService {

    @Autowired
    private CommunityDAO communityDAO;

    @Autowired
    private EmergencyTypeDAO emergencyTypeDAO;

    @Override
    public UUID saveCommunity(Community community) {
        return communityDAO.saveCommunity(community);
    }

    @Override
    public CommunityDTOForCommunity getCommunityDTO(UUID id){
        var community = communityDAO.findById(id);
        if (community == null) {
            return null;
        }
        return CommunityDTOForCommunity.from(community);
    }

    @Transactional
    @Override
    public void updateCommunity(UUID communityId, CommunityUpdateRequest request) {
        Community community = communityDAO.findById(communityId);
        if (community == null) {
            throw new RuntimeException("Сообщество не найдено");
        }

        // Обновляем поля, если они указаны в запросе
        if (request.getCommunityName() != null) {
            community.setCommunityName(request.getCommunityName());
        }
        if (request.getDescription() != null) {
            community.setDescription(request.getDescription());
        }
        if (request.getCountry() != null) {
            community.setCountry(request.getCountry());
        }
        if (request.getCity() != null) {
            community.setCity(request.getCity());
        }
        if (request.getDistrict() != null) {
            community.setDistrict(request.getDistrict());
        }

        // Обновление Many-to-Many связи с проверкой
        if (request.getEmergencyTypeIds() != null) {
            List<EmergencyTypes> emergencyTypes = emergencyTypeDAO.findAllById(request.getEmergencyTypeIds());

            // Проверка существования всех ID
            if (emergencyTypes.size() != request.getEmergencyTypeIds().size()) {
                Set<Integer> foundIds = emergencyTypes.stream()
                        .map(EmergencyTypes::getId)
                        .collect(Collectors.toSet());
                List<Integer> missingIds = request.getEmergencyTypeIds().stream()
                        .filter(id -> !foundIds.contains(id))
                        .toList();
                throw new RuntimeException("Не найдены типы ЧС с ID: " + missingIds);
            }

            // Корректное обновление коллекции для Hibernate
            community.getMonitoredEmergencyTypes().clear();
            community.getMonitoredEmergencyTypes().addAll(emergencyTypes);
        }

        communityDAO.saveCommunity(community);
    }
}
