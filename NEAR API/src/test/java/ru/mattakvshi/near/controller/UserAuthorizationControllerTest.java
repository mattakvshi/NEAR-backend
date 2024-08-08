package ru.mattakvshi.near.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.mattakvshi.near.config.security.JWTProvider;
import ru.mattakvshi.near.controller.user.UserAuthorizationController;
import ru.mattakvshi.near.dto.auth.UserRegistrationRequest;
import ru.mattakvshi.near.entity.NotificationOptions;
import ru.mattakvshi.near.service.UserAccountService;
import ru.mattakvshi.near.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

//Аннотации:
//
//@WebMvcTest(UserAuthorizationController.class): Загружает только веб-слой приложения для тестирования контроллера.
//@MockBean: Создает моки для зависимостей контроллера.
//Методы тестирования:
//
//testRegisterUser(): Тестирует регистрацию пользователя.
//testAuthUser(): Тестирует авторизацию пользователя.
//testGetNewAccessTokenForUser(): Тестирует получение нового access токена.
//testGetCurrentUser(): Тестирует получение информации о текущем пользователе.
//Вспомогательные методы:
//
//asJsonString(Object obj): Преобразует объект в JSON строку для использования в теле запросов.
//Эти тесты используют MockMvc для выполнения HTTP запросов к контроллеру и проверки ответов и поведения контроллера.

@RunWith(SpringRunner.class)
@WebMvcTest(UserAuthorizationController.class)
public class UserAuthorizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAccountService userAccountService;

    @MockBean
    private UserService userService;

    @MockBean
    private JWTProvider jwtProvider;

    @Test
    public void testRegisterUser() throws Exception {
        List<NotificationOptions> notificationOptions = new ArrayList<>();
        notificationOptions.add(
                NotificationOptions.builder()
                        .id(1)
                        .title("Email")
                        .build()
        );
        notificationOptions.add(
                NotificationOptions.builder()
                        .id(2)
                        .title("Telegram")
                        .build()
        );
        notificationOptions.add(
                NotificationOptions.builder()
                        .id(3)
                        .title("Mobile Notification")
                        .build()
        );
        UserRegistrationRequest request = UserRegistrationRequest.builder()
                .userName("Test test")
                .email("example@gmail.com")
                .password("123321")
                .phoneNumber("+7 (918) 234-56-78")
                .telegramShortName("example")
                .location("Россия, example, test")
                .birthday(LocalDate.parse("2002-02-02"))
                .selectedOptions(notificationOptions)
                .build();

        mockMvc.perform(post("/signup/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
