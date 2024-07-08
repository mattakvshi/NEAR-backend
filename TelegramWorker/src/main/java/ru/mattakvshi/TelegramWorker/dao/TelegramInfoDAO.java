package ru.mattakvshi.TelegramWorker.dao;

import org.springframework.stereotype.Component;
import ru.mattakvshi.TelegramWorker.entity.TelegramUserInfo;
import ru.mattakvshi.TelegramWorker.entity.TelegramUserInfoId;

@Component
public interface TelegramInfoDAO {

    TelegramUserInfo saveUserInfo(TelegramUserInfo userInfo);

    TelegramUserInfo getUserInfoById(TelegramUserInfoId id);
}
