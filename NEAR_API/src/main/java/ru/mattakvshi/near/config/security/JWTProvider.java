package ru.mattakvshi.near.config.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

//Теперь создадим компонент JwtProvider. Он будет генерировать и валидировать access и refresh токены.

@Slf4j
@Component
public class JWTProvider {

    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;

    //В конструктор мы передаем секретные ключи, для подписи и валидации токенов.

    //Почему два секретных ключа?
    //Один будет использоваться для генерации access токена, а второй для генерации refresh токена.
    // Это позволит нам создать отдельные сервисы с бизнес логикой, которые не будут выдавать токены,
    // но зная ключ от access токена смогут их валидировать. Но эти сервисы не будут знать ключ от refresh токена,
    // а значит если какой-то из этих сервисов будет скомпрометирован, то мы просто заменим ключ от access токена,
    // и не придется разлогинивать всех пользователей из-за того что их refresh токены станут не валидны.


    //С помощью аннотации @Value Spring подставляет значение из файла application.properties.
    //Поэтому нужно записать туда значения ключей в формате Base64:
    //
    //jwt.secret.access=qBTmv4oXFFR2GwjexDJ4t6fsIUIUhhXqlktXjXdkcyygs8nPVEwMfo29VDRRepYDVV5IkIxBMzr7OEHXEHd37w==
    //jwt.secret.refresh=zL1HB3Pch05Avfynovxrf/kpF9O2m4NCWKJUjEp27s9J2jEG3ifiKCGylaZ8fDeoONSTJP/wAzKawB8F9rOMNg==

    //Как получить ключи?
    //
    //Самым надежным способом будет воспользоваться методом Keys.secretKeyFor(),
    // который генерирует надежные ключи, но он возвращает объект SecretKey.
    // Нам же нужно как-то получить текстовую строку, чтобы использовать ее в application.properties.
    //
    //Для этого можно получить массив байт ключа, используя метод  SecretKey.getEncoded(),
    // и преобразовать их в Base64. Этот механизм я описал в классе GenerateKeys.
    // Можно просто запустить этот класс и получить два ключа.
    //
    //Обратите внимание на конструктор JwtProvider, там происходит обратный процесс.
    // Мы преобразуем Base64 обратно в массив байт, после чего используем Keys.hmacShaKeyFor(),
    // чтобы восстановить из этих байтов объект ключа SecretKey.


    public JWTProvider(
            @Value("${jwt.secret.access}") String jwtAccessSecret,
            @Value("${jwt.secret.refresh}") String jwtRefreshSecret
    ) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
    }

    //Метод generateAccessToken принимает объект пользователя и генерирует access токен для него.


    public String generateAccessToken(@NonNull UserDetails userDetails) {
        // Строки ниже отвечают за определение времени жизни токена.
        // В данном случае это пять минут. Библиотека, которая генерирует токены,
        // не работает с LocalDateTime, поэтому приходится конвертировать все в старый формат Date.
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(10).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        //Строки ниже отвечают за непосредственное создание access токена.
        // В токен мы указываем логин пользователя, дату до которой токен валиден, алгоритм шифрования и наши произвольные claims:
        // роли и имя пользователя. Для примера мы добавили в токен информацию об имени пользователя,
        // чтобы на фронте не запрашивать бэкенд каждый раз, когда его нужно вывести.
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim("roles", userDetails.getAuthorities())
                .compact();
    }

    //Метод generateRefreshToken делает все тоже самое, только мы не передаем туда claims и указываем большее время жизни.
    public String generateRefreshToken(@NonNull UserDetails userDetails) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(refreshExpiration)
                .signWith(jwtRefreshSecret)
                .compact();
    }

    //Методы validateAccessToken и validateRefreshToken отвечают за проверку валидности токена.
    // Если токен протух, или подписан неправильно, то в лог запишется соотсветсвующее сообщение,
    // а метод вернет false.

    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, jwtAccessSecret);
    }

    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, jwtRefreshSecret);
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    public String getLoginFromAccessToken(@NonNull String token) {
        return getLoginFromToken(token, jwtAccessSecret);
    }

    public String getLoginFromRefreshToken(@NonNull String token) {
        return getLoginFromToken(token, jwtRefreshSecret);
    }

    private String getLoginFromToken(@NonNull String token, @NonNull Key secret) {
        return Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
