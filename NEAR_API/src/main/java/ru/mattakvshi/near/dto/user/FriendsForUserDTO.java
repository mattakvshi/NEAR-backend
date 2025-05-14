package ru.mattakvshi.near.dto.user;

import lombok.Builder;
import lombok.Data;
import ru.mattakvshi.near.dto.user.UserDTOForUser;
import java.util.List;

@Data
@Builder
public class FriendsForUserDTO {
    private List<UserDTOForUser> friends;
    private List<UserDTOForUser> sentRequests;
    private List<UserDTOForUser> receivedRequests;
}
