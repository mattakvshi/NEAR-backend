package ru.mattakvshi.near.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import ru.mattakvshi.near.dto.community.UserDTOForCommunity;
import ru.mattakvshi.near.entity.Group;
import ru.mattakvshi.near.entity.base.User;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class GroupDTOForUser implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private UUID id;

    private String groupName;

    private List<UserDTOForCommunity> members;

    public static GroupDTOForUser from(Group group) {
        GroupDTOForUser groupDTO = new GroupDTOForUser();
        groupDTO.setId(group.getId());
        groupDTO.setGroupName(group.getGroupName());

        groupDTO.setMembers(
                group.getMembers()
                        .stream()
                        .map(UserDTOForCommunity::from)
                        .collect(Collectors.toList())
        );

        return groupDTO;
    }

}
