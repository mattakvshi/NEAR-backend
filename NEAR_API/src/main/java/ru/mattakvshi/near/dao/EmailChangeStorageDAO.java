package ru.mattakvshi.near.dao;

import ru.mattakvshi.near.entity.auth.EmailChangeStorage;
import ru.mattakvshi.near.entity.auth.EmailVerificationToken;

import java.util.UUID;

public interface EmailChangeStorageDAO {

    void save(EmailChangeStorage emailChangeStorage);

    void delete(EmailChangeStorage emailChangeStorage);

    EmailChangeStorage findById(UUID token);
}
