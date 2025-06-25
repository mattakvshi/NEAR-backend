package ru.mattakvshi.near.service;

public interface MailSender {
    void send(String emailTo, String subject, String body);
}
