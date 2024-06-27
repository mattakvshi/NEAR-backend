package ru.mattakvshi.near.service;

import jakarta.transaction.Transactional;
import ru.mattakvshi.near.entity.auth.CommunityAccount;
import ru.mattakvshi.near.entity.auth.UserAccount;

public interface CommunityAccountService {
    @Transactional
    CommunityAccount saveCommunity(CommunityAccount communityAccount);

    CommunityAccount findByEmail(String email);

    CommunityAccount findByEmailAndPassword(String email, String password);
}
