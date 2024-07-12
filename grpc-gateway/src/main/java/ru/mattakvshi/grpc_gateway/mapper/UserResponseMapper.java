package ru.mattakvshi.grpc_gateway.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.mattakvshi.grpc_gateway.dto.UserResponse;

@Mapper
public interface UserResponseMapper {
    UserResponseMapper INSTANCE = Mappers.getMapper(UserResponseMapper.class);

    ru.mattakvshi.grpccommon.UserResponse toGrpcUserResponse(String user);
}
