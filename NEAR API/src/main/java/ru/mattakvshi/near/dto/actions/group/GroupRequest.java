package ru.mattakvshi.near.dto.actions.group;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import ru.mattakvshi.near.entity.base.User;

import java.util.List;
import java.util.UUID;

@Getter
public class GroupRequest {

    private String groupName;

    private List<UUID> members;

}
