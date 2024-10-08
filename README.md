# NEAR-backend

## Used stack

- Java
- Spring
- PostgreSQL
- MongoDB
- Redis
- Apache Kafka
- Doker
- Kubernetes
- Nginx

# Implemented services:

The architecture is built according to the Citadel pattern:

- [NEAR API](https://github.com/mattakvshi/NEAR-backend/tree/main/NEAR%20API)  - The main service that contains all the endpoints and is responsible for interacting with the client software. This service processes all user information, works with the main database and transmits the necessary data to Kafka when sending notifications. 
- [TelegeramWorker](https://github.com/mattakvshi/NEAR-backend/tree/main/TelegramWorker) - The worker service for sending notifications via telegram.
- [EmailWorker](https://github.com/mattakvshi/NEAR-backend/tree/main/EmailWorker) - The worker service for sending notifications via mail. 
- [MobilePushWorker](https://github.com/mattakvshi/NEAR-backend/tree/main/MobilePushWorker) - The worker service for sending notifications via push notification. 
- [gRPC-gateway](https://github.com/mattakvshi/NEAR-backend/tree/main/grpc-gateway) - A service for connecting the tg bot and the main API.

![1](https://github.com/mattakvshi/NEAR-frontend/blob/master/Макеты%20frontend/Презентация%20продукта/71c5bd96130e7b6d96c14cdbc53d7383-9.png?raw=true)
![2](https://github.com/mattakvshi/NEAR-frontend/blob/master/Макеты%20frontend/Презентация%20продукта/71c5bd96130e7b6d96c14cdbc53d7383-10.png?raw=true)

# More info

More information can be found in README.md a repository with a [frontend](https://github.com/mattakvshi/NEAR-frontend).

![3](https://github.com/mattakvshi/NEAR-frontend/blob/master/Макеты%20frontend/Log%20in%20group/NEAR-landing.png?raw=true)
![4](https://github.com/mattakvshi/NEAR-frontend/blob/master/Макеты%20frontend/Презентация%20продукта/71c5bd96130e7b6d96c14cdbc53d7383-11.png?raw=true)
![5](https://github.com/mattakvshi/NEAR-frontend/blob/master/Макеты%20frontend/Log%20in%20group/NEAR-LogInCommunity.png)
![6](https://github.com/mattakvshi/NEAR-frontend/blob/master/Макеты%20frontend/Презентация%20продукта/71c5bd96130e7b6d96c14cdbc53d7383-15.png?raw=true)

# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

- [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
- [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.3.1/maven-plugin/reference/html/)
- [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.3.1/maven-plugin/reference/html/#build-image)
- [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#using.devtools)
- [Spring Web](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#web)
- [Spring Security](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#web.security)
- [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)

### Guides

The following guides illustrate how to use some features concretely:

- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
- [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
- [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
- [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
- [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.
