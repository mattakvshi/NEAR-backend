package ru.mattakvshi.near.config.security.community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.entity.auth.UserAccount;
import ru.mattakvshi.near.service.impl.UserAccountService;

@Service
public class CommunityDetailsService implements UserDetailsService {

    @Autowired
    private UserAccountService userAccountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountService.findByEmail(username);
        return userAccount;
    }
}
