package ru.mattakvshi.near.dao;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.mattakvshi.near.entity.NotificationOptions;
import ru.mattakvshi.near.entity.base.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static reactor.core.publisher.Mono.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDAOImplTest {

    @MockBean
    private UserDAO userDAO;

    private static User user;

    @BeforeClass
    public static void prepareTestData() {

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

        user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@example.com");
        user.setPhoneNumber("+7 (918) 284-28-48");
        user.setCountry("USA");
        user.setCity("San Francisco");
        user.setDistrict("Hfkls");
        user.setBirthday(LocalDate.now());
        user.setTelegramShortName("afp;jlkd");
        user.setSelectedOptions(notificationOptions);

    }

    @Test
    public void saveUserTest() {
        when(userDAO.saveUser(user)).thenReturn()
    }

}
