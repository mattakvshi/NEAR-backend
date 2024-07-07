package ru.mattakvshi.near.dto.actions;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import ru.mattakvshi.near.entity.NotificationTemplate;
import ru.mattakvshi.near.entity.base.User;

import java.util.List;
import java.util.UUID;

@Data
public class SendTemplateRequest {

    @JsonSerialize(using = ToStringSerializer.class)
    private UUID templateId;

    @JsonSerialize(using = ToStringSerializer.class)
    private List<UUID> recipients;
}


//Я ещё ни разу не работал с микросервисами в Java Spring, начинаю только в первый раз. Моя система представляет собой приложение для уведомления пользователей о черезвычайных ситуациях, есть две основные сущности User и Community, они отдельно регистрируются, и каждый их них может создавать NotificationTemplate, ещё одна сущность для хранения информации о шаблонах уведомлений, далее сообщества могу рассылать уведомления по шаблону на всех своих подписчиков, а пользователи на выбранных друзей или на выбранные группы друзей.
//
//Так выглядит DTO для обработки запроса на рассылку уведомлений по шаблону:
//
//@Data
//public class SendTemplateRequest {
//
//    private NotificationTemplate notificationTemplate;
//
//    private List<User> recipients;
//}
//
//Так же есть контроллер, который обрабатывает запросы связанные с шаблонами уведомлений: