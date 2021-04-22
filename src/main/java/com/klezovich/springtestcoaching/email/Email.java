package com.klezovich.springtestcoaching.email;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Email {

    boolean isImportant;
    boolean needToEncrypt;
    private String targetEmailAddress;
    private String emailContent;
}