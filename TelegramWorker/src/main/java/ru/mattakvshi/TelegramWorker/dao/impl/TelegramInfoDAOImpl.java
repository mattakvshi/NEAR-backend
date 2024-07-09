package ru.mattakvshi.TelegramWorker.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mattakvshi.TelegramWorker.dao.TelegramInfoDAO;
import ru.mattakvshi.TelegramWorker.dao.repository.TelegramInfoRepository;
import ru.mattakvshi.TelegramWorker.entity.TelegramUserInfo;
import ru.mattakvshi.TelegramWorker.entity.TelegramUserInfoId;

@Component
public class TelegramInfoDAOImpl implements TelegramInfoDAO {

    @Autowired
    TelegramInfoRepository telegramInfoRepository;

    @Override
    public TelegramUserInfo saveUserInfo(TelegramUserInfo userInfo) {
        return telegramInfoRepository.save(userInfo);
    }

    @Override
    public TelegramUserInfo getUserInfoById(TelegramUserInfoId id) {
        return telegramInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User info does not exist!"));
    }
}
