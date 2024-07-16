package ru.mattakvshi.near.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import ru.mattakvshi.near.dto.user.UserDTOForUser;
//import ru.mattakvshi.near.dto.user.UserDTOForUserDeserializer;
//import ru.mattakvshi.near.dto.user.UserDTOForUserSerializer;

@Configuration
@EnableCaching
public class RedisConfiguration {

    //ObjectMapper - для адекватной работы с localedate

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, ObjectMapper objectMapper
    ) {


//        SimpleModule module = new SimpleModule();
//        module.addSerializer(UserDTOForUser.class, new UserDTOForUserSerializer());
//        module.addDeserializer(UserDTOForUser.class, new UserDTOForUserDeserializer());
//        objectMapper.registerModule(module);

        RedisCacheConfiguration config = RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeKeysWith(
                        RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(new StringRedisSerializer())
                )
                .serializeValuesWith(
                        RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper))
                );

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                .build();
    }
}


//Кеширование методов в Spring Boot зависит от конкретного случая использования и архитектуры вашего приложения.
// Однако, в большинстве случаев **правильнее кешировать методы сервисов**, а не контроллеров. Вот почему:
//
//1. **Разделение ответственности**:
// Контроллеры отвечают за обработку HTTP-запросов и передачу данных между клиентом и сервером.
// Сервисы, с другой стороны, содержат бизнес-логику. Кеширование на уровне сервисов позволяет
// отделить бизнес-логику от логики представления.
//
//2. **Повторное использование**:
// Методы сервисов могут вызываться из разных частей приложения, включая другие сервисы и контроллеры.
// Кеширование на уровне сервисов позволяет избежать дублирования кеширования и улучшить производительность.
//
//3. **Гибкость**:
// Кеширование на уровне сервисов предоставляет больше гибкости в управлении кешем.
// Вы можете легко настроить различные политики кеширования для разных методов и классов.
//
//Пример кеширования метода сервиса:
//
//```java
//@Service
//public class MyService {
//
//    @Cacheable("myCache")
//    public String getData(String param) {
//        // Долгая операция, например, запрос к базе данных
//        return "data";
//    }
//}
//```
//
//Пример кеширования метода контроллера (не рекомендуется):
//
//```java
//@RestController
//public class MyController {
//
//    @Cacheable("myCache")
//    @GetMapping("/data")
//    public String getData(@RequestParam String param) {
//        // Долгая операция, например, запрос к базе данных
//        return "data";
//    }
//}
//```
//
//Таким образом, **кеширование методов сервисов** является более предпочтительным подходом в большинстве случаев.