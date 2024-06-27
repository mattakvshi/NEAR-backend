package ru.mattakvshi.near.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dao.repository.auth.CommunityAccountRepository;
import ru.mattakvshi.near.dao.repository.auth.UserAccountRepository;
import ru.mattakvshi.near.entity.auth.CommunityAccount;
import ru.mattakvshi.near.entity.auth.UserAccount;

@Service
public class CommunityAccountService {


    @Autowired
    private CommunityAccountRepository communityAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; //Позволяет шифровать пороли и записывать в базу в зашифрованном виде

    public CommunityAccount saveCommunity(CommunityAccount communityAccount) {;
        communityAccount.setPassword(passwordEncoder.encode(communityAccount.getPassword()));
        return communityAccountRepository.save(communityAccount);
    }

    public CommunityAccount findByEmail(String email) {
        return communityAccountRepository.findByEmail(email);
    }

    public CommunityAccount findByEmailAndPassword(String email, String password) {
        CommunityAccount communityAccount = findByEmail(email);
        if (communityAccount != null) {
            if (passwordEncoder.matches(password, communityAccount.getPassword())) {
                return communityAccount;
            }
        }
        return null;
    }
}
