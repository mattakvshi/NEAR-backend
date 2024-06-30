package ru.mattakvshi.near.config.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Log
@Component
public class JWTProviderOld {
    @Value("${jwt.secret}") //Берём секретное слово из property
    private String jwtSecret;

    public String generateToken(String email) {
        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes()); //Шифруется по секретному слову создаётся ключ


        return Jwts.builder()
                .setSubject(email) // Передаём наш логин
                .setExpiration(date) //И когда он был создан
                .signWith(key) //Передаём ключ
                .compact();
    }


    //Валидируем токен, если хорошо парсится true иначе false
    public boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.severe("JWTProvider: Invalid JWT");
        }
        return false;
    }

    //Получаем логин аккаунта по токену, расшифровываем и возвращаем логин
    public String getLoginFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
