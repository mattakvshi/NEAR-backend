package ru.mattakvshi.MobilePushWorker.service;

public interface NotificationSender {
     void sendNotification(String deviceToken, String title, String body);
}
