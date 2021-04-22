package com.klezovich.springtestcoaching.email;

import org.springframework.stereotype.Component;

@Component
public class EmailSdk {

    //If you call this method a real email will be sent
    public boolean sendEmail(String emailAddress, String emailContent) {
        return true;
    }
}
