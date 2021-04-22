package com.klezovich.springtestcoaching.mockito;

import com.klezovich.springtestcoaching.email.Email;

//Class repsonsibility is to create test objects
public class EmailMother {

    //Standard object configurations ...
    static Email.EmailBuilder valid() {
        return Email.builder()
                .targetEmailAddress("aws@aws.com")
                .emailContent("Hello")
                .isImportant(false)
                .needToEncrypt(false);
    }

    static Email.EmailBuilder mustBeEncrypted() {
        return valid().needToEncrypt(true);
    }

    static Email.EmailBuilder important() {
        return valid().isImportant(true);
    }
}
