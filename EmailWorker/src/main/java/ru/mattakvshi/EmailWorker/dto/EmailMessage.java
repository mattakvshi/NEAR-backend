package ru.mattakvshi.EmailWorker.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class EmailMessage extends NotificationMessage{
    private String email;

    @Override
    public String toString(){
        return super.toString() + "\n EmailMessage [Email: " + email + "]\n";
    }
}
