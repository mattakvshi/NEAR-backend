package ru.mattakvshi.near.config.security.community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.entity.auth.CommunityAccount;
import ru.mattakvshi.near.service.CommunityAccountService;
import ru.mattakvshi.near.service.impl.CommunityAccountServiceImpl;

@Service
public class CommunityDetailsService implements UserDetailsService {

    @Autowired
    private CommunityAccountService communityAccountService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CommunityAccount communityAccount = communityAccountService.findByEmail(email);
        return communityAccount;
    }
}
