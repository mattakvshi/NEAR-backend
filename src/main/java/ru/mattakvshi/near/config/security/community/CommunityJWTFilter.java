package ru.mattakvshi.near.config.security.community;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.mattakvshi.near.entity.auth.CommunityAccount;

import java.io.IOException;

import static io.jsonwebtoken.lang.Strings.hasText;

@Log
@Component
public class CommunityJWTFilter extends GenericFilterBean {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private CommunityJWTProvider communityJWTProvider;

    @Autowired
    private CustomCommunityDetailsService communityDetailsService;

    //Каждый раз как будет приходить запрос мы по токену будем вытаскивать пользователя и вставлять в SecurityContextHolder,
    // это нужно чтобы далее получать пользователя который пришёл по этому токену

    //На низком уровне всё работает на сервлетах

    //ServletRequest - хранит всю информацию о прешедшем запросе

    //ServletResponse - хранит всю инфу о возвращаемом ответе

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("CommunityJWTFilter do filter...");
        String token = getTokenFromRequests((HttpServletRequest) servletRequest);
        if (token != null && communityJWTProvider.validateToken(token)) {
            String userLogin = communityJWTProvider.getLoginFromToken(token);
            CommunityAccount communityAccount = (CommunityAccount) communityDetailsService.loadUserByUsername(userLogin);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(communityAccount, null, communityAccount.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequests(HttpServletRequest servletRequest) {
        String bearer = servletRequest.getHeader(AUTHORIZATION_HEADER); // Ищем в http запросе заголовок с ключём Authorization и вытаскиваем Bearer token
        if (hasText(bearer) && bearer.startsWith("Bearer ")){
            return bearer.substring(7);
        }
        return null;
    }
}
