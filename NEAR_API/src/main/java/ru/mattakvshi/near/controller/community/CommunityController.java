package ru.mattakvshi.near.controller.community;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mattakvshi.near.controller.BaseController;
import ru.mattakvshi.near.dto.community.CommunityDTOForCommunity;
import ru.mattakvshi.near.dto.community.CommunityUpdateRequest;
import ru.mattakvshi.near.dto.user.CommunityDTOForUser;
import ru.mattakvshi.near.dto.user.UserDTOForUser;
import ru.mattakvshi.near.service.CommunityAccountService;
import ru.mattakvshi.near.service.CommunityService;
import ru.mattakvshi.near.service.impl.auth.CommunityAccountServiceImpl;

import java.util.UUID;

@Slf4j
@Tag(name = "CommunityBaseController", description = "Контроллер для работы с сообществами")
@RestController
public class CommunityController extends BaseController {

    @Autowired
    CommunityService communityService;

    @Autowired
    CommunityAccountService communityAccountService;

    @GetMapping("/community/get")
    public ResponseEntity<CommunityDTOForCommunity> getCommunity(@RequestParam UUID id) {
        var communityDTO = communityService.getCommunityDTO(id);
        if (communityDTO == null) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }
        return new ResponseEntity<>(communityDTO, HttpStatusCode.valueOf(200));
    }

    @Operation(
            summary = "Эндпоинт для обновления данных сообщества",
            description = "Можно хоть один элемент передавать в JSON хоть все, передавай только то, что поменялось, такой вот вари оптимизации."
    )
    @PatchMapping("/community/update")
    public ResponseEntity<?> updateCommunity(@RequestBody @Valid CommunityUpdateRequest request) {
        try {
            UUID communityId = communityAccountService.getCurrentCommunityUUID(); // Получите ID текущего сообщества
            communityService.updateCommunity(communityId, request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Ошибка обновления данных сообщества", e);
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @Operation(
            summary = "Эндпоинт для получения выбранных пользователем каналов оповещения"
    )
    @GetMapping("/community/get-emergency-type")
    public ResponseEntity<?> getEmergencyTypeForCommunities() {
        try{
            return ResponseEntity.ok(communityService.getCommunity(communityAccountService.getCurrentCommunityUUID()).getMonitoredEmergencyTypes());
        } catch (AuthException ae) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }
    }

    @GetMapping("/community/all")
    @Operation(
            summary = "Получить список сообществ с пагинацией и поиском.",
            description = "На данный момент эндпоинт принимает 3 параметра: search - тег для поиска по имени или описнию всообщества, page - получаемая страница из набора всех сообществ, size - колличество элементов на странице"
    )
    public ResponseEntity<Page<CommunityDTOForUser>> getCommunities(
            @RequestParam(name = "search", required = false) String searchQuery,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        Page<CommunityDTOForUser> communities = communityService.searchCommunities(searchQuery, page, size);
        return ResponseEntity.ok(communities);
    }

}
