package ru.mattakvshi.near.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import ru.mattakvshi.near.dto.user.UserDTOForUser;

import java.time.Duration;
//import ru.mattakvshi.near.dto.user.UserDTOForUserDeserializer;
//import ru.mattakvshi.near.dto.user.UserDTOForUserSerializer;

@Configuration
@EnableCaching
public class RedisConfiguration {

    //ObjectMapper - для адекватной работы с localedate

//    @Bean
//    public RedisCacheConfiguration cacheConfiguration() {
//        return RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofMinutes(60))
//                .disableCachingNullValues()
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
//    }
//
//    @Bean
//    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
//        return (builder) -> builder
//                .withCacheConfiguration("itemCache",
//                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
//                .withCacheConfiguration("customerCache",
//                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)));
//    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY
        );

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

//    @Bean
//    @SuppressWarnings("all")
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(factory);
//
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.activateDefaultTyping(
//                LaissezFaireSubTypeValidator.instance,
//                ObjectMapper.DefaultTyping.NON_FINAL,
//                JsonTypeInfo.As.PROPERTY
//        );
//
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        template.setKeySerializer(stringRedisSerializer);
//        template.setHashKeySerializer(stringRedisSerializer);
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
//
//        template.afterPropertiesSet();
//        return template;
//    }
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