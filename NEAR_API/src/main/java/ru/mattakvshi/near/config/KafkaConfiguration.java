package ru.mattakvshi.near.config;


import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.mattakvshi.near.dto.notice.NotificationMessage;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configs.put(AdminClientConfig.CLIENT_ID_CONFIG, "near-admin");
        return new KafkaAdmin(configs);
    }

    @Bean
    public ProducerFactory<String, NotificationMessage> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        JsonSerializer<NotificationMessage> serializer = new JsonSerializer<>();
        serializer.setAddTypeInfo(true);

        return new DefaultKafkaProducerFactory<>(configProps, new StringSerializer(), serializer);
    }

    @Bean
    public KafkaTemplate<String, NotificationMessage> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic emailTopic() {
        return new NewTopic("Email", 1, (short) 1); // имя топика, количество партиций, фактор репликации
    }

    @Bean
    public NewTopic telegramTopic() {
        return new NewTopic("Telegram", 1, (short) 1);
    }

    @Bean
    public NewTopic mobileNotificationTopic() {
        return new NewTopic("Mobile_Notification", 1, (short) 1);
    }
}


//Бины NewTopic, которые вы определили, будут автоматически создавать топики в Kafka при запуске приложения,
// если они ещё не существуют. Вам не нужно вызывать эти бины где-то в коде вручную; Spring Boot сделает это за вас.
//
//Конфигурация KafkaTemplate также правильная и позволит вам отправлять сообщения в Kafka из вашего сервиса NotificationDispatcherImpl.
// При старте приложения Spring Boot автоматически подключится к Kafka с использованием параметров,
// указанных в application.properties или application.yml, где вы должны указать kafka.bootstrap-servers.
//
//Ваш сервис NotificationDispatcherImpl правильно использует KafkaTemplate для отправки сообщений в Kafka.
// Каждый вызов метода kafkaTemplate.send() будет отправлять сообщение в указанный топик.
// Порядок сообщений не гарантируется, так как вы не используете ключи для сообщений, что подходит для вашего случая, поскольку порядок не важен.
//
//Убедитесь, что ваши docker-compose.yml настройки соответствуют конфигурации Kafka,
// и что Kafka и Zookeeper запущены и работают корректно перед запуском приложения.
//
//Вот пример того, как должны выглядеть настройки в application.properties:
//
//kafka.bootstrap-servers=localhost:9092 # Указывает на Kafka брокер, запущенный через Docker
//Или в application.yml:
//
//kafka:
//  bootstrap-servers: localhost:9092
//Если вы уже всё настроили и у вас есть соответствующие права доступа, то ваша конфигурация должна работать
// без дополнительных действий с вашей стороны. При запуске приложения Spring Boot создаст топики, если они не существуют,
// и ваш сервис будет готов к отправке сообщений в Kafka.