package ru.mattakvshi.near.config.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.service.UserAccountService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserAccountService userAccountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountService.findByEmail(username);
        if (userAccount == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        if (!userAccount.isEmailVerified()) {
            throw new DisabledException("Email не подтвержден");
        }
        return userAccount;
    }
}
