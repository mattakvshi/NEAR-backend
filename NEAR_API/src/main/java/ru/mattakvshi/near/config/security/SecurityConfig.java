package ru.mattakvshi.near.config.security;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import ru.mattakvshi.near.entity.auth.SystemRole;

import java.util.Arrays;
import java.util.Collections;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    @Lazy
    private JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Конфигурация на фулл новом API, наконец-то ничего не deprecated
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("http://31.129.107.60:8080"));
                    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setAllowCredentials(true);
                    config.setMaxAge(3600L);
                    return config;
                }))
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers("*/user/**", "/community/all", "/community/get").hasAnyAuthority(SystemRole.User.name())
                                .requestMatchers("*/community/**", "/user/all", "/user/get").hasAnyAuthority(SystemRole.Community.name())
                                .requestMatchers(
                                        "*/signup/account",
                                        "*/login/account",
                                        "*/token/account" ,
                                        "*/signup/community",
                                        "*/login/community",
                                        "*/token/community",
                                        "/v3/**",
                                        "/swagger-ui/**",
                                        "*/verify-email-user",
                                        "*/confirm-email-change-user",
                                        "*/verify-email-community",
                                        "*/confirm-email-change-community"
                                ).permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden"))
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}


//Отключение httpBasic и csrf не всегда является недостатком — всё зависит от особенностей вашего проекта и конкретных требований безопасности.
// Давайте рассмотрим это более детально:

//HTTP Basic Auth:
//Почему отключено: Использование HTTP Basic Auth не рекомендуется для современных приложений, особенно если они используют JWT для аутентификации.
// JWT (JSON Web Tokens) сами по себе обеспечивают высокий уровень безопасности и гибкости.
//Когда включать: Использовать HTTP Basic Auth можно в случае простых, внутренних API, где вам нужно быстро добавить базовую аутентификацию.
//
// CSRF (Cross-Site Request Forgery):
//Почему отключено: CSRF защита полезна для web-приложений, которые используют сессии для аутентификации.
// Однако для API, использующих JWT токены, атаки типа CSRF не столь актуальны, так как токены передаются через заголовки, а не через cookies.
//Когда включать: Если вы используете формы на клиенте, которые передают данные на сервер через HTTP-запросы (например, в традиционных web-приложениях).
// В RESTful API с JWT аутентификацией CSRF обычно не требуется.
