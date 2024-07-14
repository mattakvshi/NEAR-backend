package ru.mattakvshi.grpc_gateway.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class GroupDTOSmall {

    @JsonSerialize(using = ToStringSerializer.class)
    private UUID id;

    private String groupName;

    private List<UserDTOSmall> members;

}
