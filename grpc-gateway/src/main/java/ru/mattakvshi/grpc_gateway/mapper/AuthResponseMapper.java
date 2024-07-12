package ru.mattakvshi.grpc_gateway.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.mattakvshi.grpc_gateway.dto.AuthResponse;

import java.util.UUID;

@Mapper
public interface AuthResponseMapper {
    AuthResponseMapper INSTANCE = Mappers.getMapper(AuthResponseMapper.class);

    @Mapping(source = "uuid", target = "uuid", qualifiedByName = "uuidToString")
    ru.mattakvshi.grpccommon.AuthResponse toGrpcAuthResponse(AuthResponse authResponse);

    @Named("uuidToString")
    default String uuidToString(UUID uuid) {
        return uuid.toString();
    }
}
