package ru.mattakvshi.TelegramWorker.dao;

import ru.mattakvshi.TelegramWorker.entity.TelegramUserInfo;
import ru.mattakvshi.TelegramWorker.entity.TelegramUserInfoId;

public interface TelegramInfoDAO {

    TelegramUserInfo saveUserInfo(TelegramUserInfo userInfo);

    TelegramUserInfo getUserInfoById(TelegramUserInfoId id);
}
