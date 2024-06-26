package ru.mattakvshi.near.config.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.entity.auth.CommunityAccount;
import ru.mattakvshi.near.service.impl.CommunityAccountService;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private CommunityAccountService communityAccountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CommunityAccount communityAccount = communityAccountService.findByEmail(username);
        return communityAccount;
    }
}
