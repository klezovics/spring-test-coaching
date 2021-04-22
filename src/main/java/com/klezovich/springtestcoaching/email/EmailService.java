package com.klezovich.springtestcoaching.email;

import com.klezovich.springtestcoaching.email.exception.BadLanguageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final EmailSdk sdk;

    @Autowired
    EmailService(EmailSdk sdk) {
        this.sdk = sdk;
    }

    public boolean sendEmail(Email email) {

        if (email.isImportant) {
            email.setEmailContent("Important:" + email.getEmailContent());
        }

        //Feature 1 - Profanity filter
        // If word "fuck" is in email text - throw exception and DO NOT send the email
        if(email.getEmailContent().contains("fuck")) {
            throw new BadLanguageException();
        }

        return sdk.sendEmail(email.getTargetEmailAddress(), email.getEmailContent());
    }
}