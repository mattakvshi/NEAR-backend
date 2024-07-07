package ru.mattakvshi.near.service.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dto.notice.EmailMessage;
import ru.mattakvshi.near.dto.notice.NotificationMessage;
import ru.mattakvshi.near.dto.notice.PushMessage;
import ru.mattakvshi.near.dto.notice.TelegramMessage;
import ru.mattakvshi.near.entity.NotificationOptions;
import ru.mattakvshi.near.entity.NotificationTemplate;
import ru.mattakvshi.near.entity.base.User;
import ru.mattakvshi.near.service.NotificationDispatcher;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

@Log
@Service
public class NotificationDispatcherImpl implements NotificationDispatcher {

   @Autowired
   private KafkaTemplate<String, NotificationMessage> kafkaTemplate;

    @Override
    public void dispatch(NotificationTemplate notificationTemplate, List<User> recipients, Authentication account) {
        for (User recipient : recipients) {
            // Определение опций уведомлений для пользователя
            List<NotificationOptions> userOptions = recipient.getSelectedOptions();
            // Отправка сообщений в соответствующие топики или партиции
            userOptions.forEach(option -> {
                NotificationMessage message = createMessage(notificationTemplate, recipient, option, account);
                // Отправка сообщения в топик, соответствующий типу уведомления
                //В нашем случае порядок не важен, поэтому буду использовать перегрузку метода без ключа (Подробнее об этом снизу под классом в комментариях)
                log.info("Message: " + message);
                kafkaTemplate.send(option.getTitle(), message);
            });
        }
    }

    private NotificationMessage createMessage(NotificationTemplate template, User recipient, NotificationOptions option, Authentication account) {
        // Создание сообщения для Kafka на основе шаблона уведомления, получателя и выбранного варианта рассылки
        switch (option.getTitle()) {
            case "Email" -> {
                var message = new EmailMessage();
                message.setTemplateName(template.getTemplateName());
                message.setOwner(account.getName());
                message.setMessage(template.getMessage());
                message.setEmergencyType(template.getEmergencyType().getTitle());

                message.setEmail(recipient.getEmail());

                return message;
            }
            case "Telegram" -> {
                var message = new TelegramMessage();
                message.setTemplateName(template.getTemplateName());
                message.setOwner(account.getName());
                message.setMessage(template.getMessage());
                message.setEmergencyType(template.getEmergencyType().getTitle());

                message.setPhoneNumber(recipient.getTelegramShortName());

                return message;
            }
            case "Mobile Notification" -> {
                var message = new PushMessage();
                message.setTemplateName(template.getTemplateName());
                message.setOwner(account.getName());
                message.setMessage(template.getMessage());
                message.setEmergencyType(template.getEmergencyType().getTitle());

                message.setDeviceToken(recipient.getDeviceToken());

                return message;
            }
            default -> throw new IllegalStateException("Unexpected value: " + option.getTitle());
        }
    }
}


//В Apache Kafka, топики и партиции являются ключевыми элементами для организации и масштабирования потоков данных.
// Вот подробное объяснение каждого из них:
//
//Топики
//Топик в Kafka - это категория или имя потока данных. Топик представляет собой логический канал,
// в который производители публикуют сообщения и из которого потребители их читают.
// Вы можете создать столько топиков, сколько необходимо для разделения данных по категориям или источникам.
//
//Партиции
//Партиция - это физическое деление топика. Каждый топик может быть разделён на несколько партиций,
// которые распределены по разным серверам в кластере Kafka (называемых брокерами).
// Партиции позволяют параллельно обрабатывать данные, так как каждая партиция может обслуживаться отдельным потребителем в рамках группы потребителей.
//
//Работа с партициями и топиками:
//Разделение на топики: Если у вас есть чётко разграниченные категории данных или сервисы,
// которые должны работать с разными типами сообщений, вы можете создать для каждой категории отдельный топик.
//Разделение на партиции: Если вам нужно масштабировать обработку данных внутри одного топика,
// вы можете увеличить количество партиций. Это позволит нескольким потребителям в группе потребителей читать данные параллельно,
// каждый из своей партиции.
//Пример:
//Представим, что у вас есть два сервиса: один обрабатывает уведомления о заказах, а другой - логи системы.
// Вы можете создать два топика: order-notifications и system-logs. Если order-notifications должен обрабатываться
// очень быстро и требует масштабирования, вы можете увеличить количество партиций в этом топике,
// чтобы несколько потребителей могли обрабатывать уведомления параллельно.
//
//Важно помнить, что каждое сообщение в партиции имеет свой уникальный идентификатор,
// называемый offset, который используется потребителями для отслеживания,
// какие сообщения были прочитаны.
//
//Использование партиций и топиков в Kafka позволяет вам гибко настраивать систему обработки данных,
// обеспечивая необходимую производительность и масштабируемость.


//KafkaTemplate - это класс из библиотеки Spring для работы с Apache Kafka,
// который упрощает отправку сообщений в топик Kafka. Он обеспечивает высокоуровневый интерфейс для отправки сообщений.
//
//Вот как работает KafkaTemplate:
//
//Создание экземпляра: Для начала работы необходимо создать экземпляр KafkaTemplate. Обычно это делается с помощью конфигурации Spring.
//Отправка сообщений: Вы можете использовать методы send, чтобы отправить сообщение в топик.
// KafkaTemplate предоставляет несколько перегрузок метода send для различных сценариев.
//Ключ и значение в KafkaTemplate<String, NotificationMessage>:
//
//Ключ (String): Ключ сообщения используется Kafka для определения партиции, в которую будет помещено сообщение.
// Если ключ не указан, сообщение будет распределено по партициям случайным образом.
//Значение (NotificationMessage): Это само сообщение, которое вы хотите отправить.
// В вашем случае это объект типа NotificationMessage.


//Эти две перегрузки метода send в KafkaTemplate отличаются наличием ключа в сообщении. Давайте рассмотрим их подробнее:
//
//Без ключа:
//public CompletableFuture<SendResult<K, V>> send(String topic, @Nullable V data) {
//    ProducerRecord<K, V> producerRecord = new ProducerRecord(topic, data);
//    return this.observeSend(producerRecord);
//}
//Этот метод используется, когда вам не нужно указывать ключ для сообщения.
// Сообщения будут распределены по партициям топика случайным образом или согласно заданной политике балансировки.
//
//С ключом:
//public CompletableFuture<SendResult<K, V>> send(String topic, K key, @Nullable V data) {
//    ProducerRecord<K, V> producerRecord = new ProducerRecord(topic, key, data);
//    return this.observeSend(producerRecord);
//}
//Этот метод позволяет указать ключ для сообщения. Ключ используется Kafka для определения,
// в какую партицию будет помещено сообщение. Если ключи сообщений совпадают,
// Kafka гарантирует, что они попадут в одну и ту же партицию.
//
//Ключи используются для:
//
//Гарантии порядка сообщений внутри одной партиции.
//Группировки связанных сообщений, чтобы они обрабатывались одним и тем же потребителем.
//Топики используются для:
//
//Логического разделения потоков данных.
//Организации сообщений по категориям или источникам.
//Использование ключей полезно, когда важен порядок сообщений или когда необходимо,
// чтобы сообщения одного типа обрабатывались вместе. Если порядок не важен, можно использовать первую перегрузку без ключа.



//СЕБЕ ПРИМЕЧАНИЕ:

//Когда вы отправляете объект через `KafkaTemplate`, сохраняется вся структура объекта, включая поля как родительского,
// так и дочернего классов. В Java, когда вы приводите объект дочернего класса к типу родительского класса,
// объект не теряет своих дочерних свойств. Он по-прежнему остается объектом дочернего класса, но с точки зрения кода,
// который работает с ним, он представлен как экземпляр родительского класса.
//
//В вашем случае, `EmailMessage` и `TelegramMessage` являются расширениями `NotificationMessage`.
// Когда вы создаете и отправляете эти объекты через `kafkaTemplate.send`, Kafka сериализует полный объект,
// включая все поля, определенные в дочернем классе. Это означает, что если `EmailMessage` имеет дополнительные поля,
// которых нет в `NotificationMessage`, они также будут сериализованы и отправлены.
//
//Однако важно убедиться, что сериализатор, настроенный для `KafkaTemplate`,
// может корректно обработать эти дочерние классы. Если вы используете стандартный сериализатор Java,
// он будет сериализовать весь объект, включая приватные поля дочернего класса. Если же используется кастомный сериализатор,
// например, для JSON, убедитесь, что он настроен на сериализацию всех необходимых полей объекта.
//
//Также стоит отметить, что при десериализации сообщения на стороне потребителя, вам нужно будет знать,
// какой именно дочерний класс использовать для восстановления объекта, чтобы корректно обработать все его поля.



//ЕЩЁ ПРИМЕЧАНИЕ

//После вызова метода `kafkaTemplate.send(option.getTitle(), message)`, сообщение будет помещено в буфер отправки и в
// конечном итоге отправлено в указанный топик Kafka. Вот что происходит в процессе:
//
//1. **Сериализация**: Объект `message` сериализуется с использованием настроенного сериализатора,
// который преобразует объект Java в байтовый массив, чтобы его можно было отправить через сеть.
//
//2. **Размещение в партиции**: Если ключ не указан, Kafka автоматически распределит сообщение по партициям топика,
// используя либо случайное распределение, либо алгоритм балансировки нагрузки.
//
//3. **Отправка**: Сериализованное сообщение отправляется в Kafka брокер, который управляет соответствующим топиком и партицией.
//
//4. **Подтверждение**: После успешной отправки сообщения Kafka брокер отправляет подтверждение обратно в `KafkaTemplate`,
// которое можно использовать для проверки статуса отправки.
//
//Чтобы считать эти сообщения с другого сервиса, вам нужно будет настроить потребителя Kafka (Kafka Consumer).
// Вот основные шаги для этого:
//
//1. **Создание потребителя**: Создайте экземпляр Kafka Consumer и настройте его с правильными параметрами,
// такими как адреса брокеров, группа потребителей, десериализаторы и т.д.
//
//2. **Подписка на топик**: Потребитель должен подписаться на топик (или топики), из которых он хочет читать сообщения.
//
//3. **Чтение сообщений**: Потребитель будет опрашивать Kafka для получения новых сообщений.
// Это может быть реализовано в цикле, который регулярно вызывает метод `poll`.
//
//4. **Десериализация**: Когда сообщения получены, они десериализуются обратно в объекты Java.
//
//5. **Обработка сообщений**: После десериализации сообщения могут быть обработаны в соответствии с логикой вашего приложения.
//
//6. **Подтверждение обработки**: После успешной обработки сообщения, потребитель должен подтвердить обработку,
// чтобы Kafka мог отметить сообщения как прочитанные.
//
//Вот пример кода, который иллюстрирует создание и настройку Kafka Consumer:
//
//```java
//import org.apache.kafka.clients.consumer.Consumer;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//
//import java.util.Collections;
//import java.util.Properties;
//
//public class NotificationConsumer {
//
//    public static void main(String[] args) {
//        Properties props = new Properties();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, NotificationMessageDeserializer.class.getName());
//
//        try (Consumer<String, NotificationMessage> consumer = new KafkaConsumer<>(props)) {
//            consumer.subscribe(Collections.singletonList("notification-topic"));
//
//            while (true) {
//                ConsumerRecords<String, NotificationMessage> records = consumer.poll(100);
//                records.forEach(record -> {
//                    // Обработка каждого сообщения
//                    NotificationMessage message = record.value();
//                    // ... ваша логика обработки ...
//                });
//            }
//        }
//    }
//}
//```
//
//Обратите внимание, что вам нужно будет реализовать `NotificationMessageDeserializer` для десериализации сообщений обратно
// в объекты `NotificationMessage`. Также не забудьте заменить "localhost:9092" и "notification-topic"
// на фактические адреса вашего Kafka брокера и имя топика, соответственно.


//НАДЕЮСЬ ПОСЛЕДНЕЕ ПРИМЕЧАНИЕ

//Для идентификации Android устройства и отправки пуш-уведомлений конкретному устройству,
// вам потребуется использовать уникальный токен устройства, который обычно предоставляется сервисами пуш-уведомлений,
// такими как Firebase Cloud Messaging (FCM). Вот основные шаги для реализации этого процесса:
//
//1. **Получение токена устройства**:
// Когда приложение на Android устройстве запускается впервые или когда токен обновляется,
// оно должно получить уникальный токен от FCM.
//
//2. **Отправка токена на сервер**:
// Приложение отправляет этот токен на ваш сервер, где он может быть связан с учетной записью пользователя.
//
//3. **Хранение токена**:
// На сервере вы сохраняете токен в базе данных, связывая его с учетной записью пользователя,
// чтобы знать, куда отправлять уведомления.
//
//4. **Отправка уведомления**:
// Когда вы хотите отправить уведомление, вы используете сохраненный токен для адресации уведомления конкретному устройству через FCM.
//
//Вот примерный код для Android приложения, который получает токен и отправляет его на сервер:
//
//```java
//FirebaseInstanceId.getInstance().getInstanceId()
//        .addOnCompleteListener(task -> {
//            if (!task.isSuccessful()) {
//                // Обработка ошибки получения токена
//                return;
//            }
//            // Получение токена
//            String token = task.getResult().getToken();
//            // Отправка токена на ваш сервер
//            sendRegistrationToServer(token);
//        });
//
//private void sendRegistrationToServer(String token) {
//    // Здесь ваш код для отправки токена на сервер
//}
//```
//
//И на стороне сервера, вы можете использовать этот токен для отправки уведомлений:
//
//```java
//public void sendPushNotification(String deviceToken, String message) {
//    // Создание объекта уведомления с использованием FCM API
//    // Отправка уведомления на устройство с помощью токена
//}
//```
//
//Не забудьте обеспечить безопасность процесса обмена токенами, используя защищенное соединение и аутентификацию,
// чтобы предотвратить злоупотребления и гарантировать, что уведомления отправляются только на подлинные устройства пользователей.