package ru.mattakvshi.near.config.security;


import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.mattakvshi.near.config.security.community.CommunityJWTProvider;
import ru.mattakvshi.near.config.security.user.UserJWTProvider;
import ru.mattakvshi.near.entity.auth.SystemRole;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserJWTProvider userJWTFilter;

    @Autowired
    private CommunityJWTProvider communityJWTFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers("/user/*").hasAnyAuthority(SystemRole.User.name())
                                .requestMatchers("/community/*").hasAnyAuthority(SystemRole.Community.name())
                                .requestMatchers("/signup/account", "/login/account", "/signup/community", "/login/community").permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> httpBasic.disable())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore((Filter) userJWTFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore((Filter) communityJWTFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
