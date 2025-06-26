package ru.mattakvshi.near.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import ru.mattakvshi.near.dto.community.CommunityDTOForCommunity;
import ru.mattakvshi.near.dto.community.CommunityUpdateRequest;
import ru.mattakvshi.near.dto.user.CommunityDTOForUser;
import ru.mattakvshi.near.dto.user.UserDTOForUser;
import ru.mattakvshi.near.entity.base.Community;

import java.util.UUID;

public interface CommunityService {

    UUID saveCommunity(Community community);

    Community getCommunity(UUID id);

    CommunityDTOForCommunity getCommunityDTO(UUID id);

    void updateCommunity(UUID communityId, CommunityUpdateRequest request);

    Page<CommunityDTOForUser> searchCommunities(String query, int page, int size);
}
