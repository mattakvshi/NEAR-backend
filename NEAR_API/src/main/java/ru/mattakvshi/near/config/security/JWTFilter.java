package ru.mattakvshi.near.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.mattakvshi.near.SystemConstants;
import ru.mattakvshi.near.config.security.community.CustomCommunityDetailsService;
import ru.mattakvshi.near.config.security.user.CustomUserDetailsService;

import java.io.IOException;

import static io.jsonwebtoken.lang.Strings.hasText;

@Log
@Component
public class  JWTFilter extends GenericFilterBean {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    @Lazy
    private CustomUserDetailsService userDetailsService;

    @Autowired
    @Lazy
    private CustomCommunityDetailsService communityDetailsService;

    //Каждый раз как будет приходить запрос мы по токену будем вытаскивать пользователя и вставлять в SecurityContextHolder,
    // это нужно чтобы далее получать пользователя, который пришёл по этому токену

    //На низком уровне всё работает на сервлетах

    //ServletRequest - хранит всю информацию о прешедшем запросе

    //ServletResponse - хранит всю инфу о возвращаемом ответе

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        try {

        var token = getTokenFromRequests((HttpServletRequest) servletRequest);
        var requestURI = ((HttpServletRequest) servletRequest).getRequestURI();

        if (token != null && jwtProvider.validateAccessToken(token)) {
            var email = jwtProvider.getLoginFromAccessToken(token);
            UserDetails userDetails = null;

            if (requestURI.startsWith(SystemConstants.BASE_URL + "/user/")) {
                log.info("JWTFilter do filter user...");
                userDetails  = userDetailsService.loadUserByUsername(email);
            }
            else if (requestURI.startsWith(SystemConstants.BASE_URL + "/community/")) {
                log.info("JWTFilter do filter community...");
                userDetails  = communityDetailsService.loadUserByUsername(email);
            }
            if (userDetails != null) {
                var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (JwtException e) { // Ловим ошибки валидации токена
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        }
    }

    private String getTokenFromRequests(HttpServletRequest servletRequest) {
        String bearer = servletRequest.getHeader(AUTHORIZATION_HEADER); // Ищем в http запросе заголовок с ключём Authorization и вытаскиваем Bearer token
        if (hasText(bearer) && bearer.startsWith("Bearer ")){
            return bearer.substring(7);
        }
        return null;
    }
}
