package ru.mattakvshi.grpc_gateway.dto.user;

import lombok.Data;


@Data
public class NotificationOptions {

    private int id;

    private String title;

    private String color;

    private String bgColor;

    private String colorDark;

    private String bgColorDark;

}
