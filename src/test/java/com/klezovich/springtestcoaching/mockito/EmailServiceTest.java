package com.klezovich.springtestcoaching.mockito;

import com.klezovich.springtestcoaching.email.Email;
import com.klezovich.springtestcoaching.email.EmailSdk;
import com.klezovich.springtestcoaching.email.EmailService;
import com.klezovich.springtestcoaching.email.exception.BadLanguageException;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

//TODO Read about object mother pattern https://reflectoring.io/objectmother-fluent-builder/
@SpringBootTest(classes = EmailService.class)
public class EmailServiceTest {

    @Autowired
    private EmailService service;

    //Creates mock and adds it to application context
    @MockBean //TODO Read about @SpyBean
    private EmailSdk sdk;


    @Test
    void testEmailService() {
        var email = EmailMother.valid().build();

        service.sendEmail(email);

        var addressCaptor = ArgumentCaptor.forClass(String.class);
        var emailTextCaptor = ArgumentCaptor.forClass(String.class);


        //Ask the mock - with which arguments were you called ?
        verify(sdk).sendEmail(addressCaptor.capture(),emailTextCaptor.capture());

        assertEquals("aws@aws.com",addressCaptor.getValue());
        assertEquals("Hello",emailTextCaptor.getValue());
    }

    @Test
    void testBadLanguageIsFilteredOut() {
        //We can customize the objects in test if we want
        var email = EmailMother.valid().emailContent("abc fuck abc").build();

        assertThrows(BadLanguageException.class, () -> service.sendEmail(email));

        //Method was never called with any arguments
        verify(sdk,never()).sendEmail(any(),any());
    }
}
