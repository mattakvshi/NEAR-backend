package ru.mattakvshi.TelegramWorker.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mattakvshi.TelegramWorker.entity.TelegramUserInfo;
import ru.mattakvshi.TelegramWorker.entity.TelegramUserInfoId;

@Repository
public interface TelegramInfoRepository extends CrudRepository<TelegramUserInfo, TelegramUserInfoId> {
}
