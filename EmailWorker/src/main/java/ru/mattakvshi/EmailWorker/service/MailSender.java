package ru.mattakvshi.EmailWorker.service;

public interface MailSender {
    void send(String emailTo, String subject, String body);
}
