package ru.mattakvshi.near.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mattakvshi.near.dao.repository.auth.UserAccountRepository;
import ru.mattakvshi.near.entity.auth.UserAccount;

import java.util.UUID;

public interface UserAccountService {
    @Transactional
    UserAccount saveUser(UserAccount userAccount);

    UserAccount findByEmail(String email);

    UserAccount findByEmailAndPassword(String email, String password);

    UserAccount findById(UUID id);
}
