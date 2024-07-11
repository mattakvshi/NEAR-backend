package ru.mattakvshi.grpc_gateway.dto;

import lombok.Data;

@Data
public class AuthRequests {

    private String email;

    private String password;

}
