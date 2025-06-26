package ru.mattakvshi.near.service;

import jakarta.transaction.Transactional;
import ru.mattakvshi.near.dto.community.CommunityDTOForCommunity;
import ru.mattakvshi.near.dto.community.CommunityUpdateRequest;
import ru.mattakvshi.near.dto.user.UserDTOForUser;
import ru.mattakvshi.near.entity.base.Community;

import java.util.UUID;

public interface CommunityService {

    UUID saveCommunity(Community community);

    Community getCommunity(UUID id);

    CommunityDTOForCommunity getCommunityDTO(UUID id);

    void updateCommunity(UUID communityId, CommunityUpdateRequest request);

}
