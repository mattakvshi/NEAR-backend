package ru.mattakvshi.grpc_gateway.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.mattakvshi.grpc_gateway.dto.AuthRequests;

@Mapper
public interface AuthRequestMapper {
    AuthRequestMapper INSTANCE = Mappers.getMapper(AuthRequestMapper.class);

    ru.mattakvshi.grpccommon.AuthRequest toGrpcAuthRequest(AuthRequests authRequests);
}
