package ru.mattakvshi.grpc_gateway.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.mattakvshi.grpc_gateway.dto.RefreshJwtRequest;

@Mapper
public interface RefreshJwtRequestMapper {
    RefreshJwtRequestMapper INSTANCE = Mappers.getMapper(RefreshJwtRequestMapper.class);

    ru.mattakvshi.grpccommon.RefreshJwtRequest toGrpcRefreshJwtRequest(RefreshJwtRequest refreshJwtRequest);
}
