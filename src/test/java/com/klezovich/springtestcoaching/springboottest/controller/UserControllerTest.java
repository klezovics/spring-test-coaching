package com.klezovich.springtestcoaching.springboottest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//TODO Read this https://reflectoring.io/spring-boot-web-controller-test/
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    //Key player - mockMvc class
    //Allow you to ...
    //1) Create requests
    //2) Verify responses
    private MockMvc mockMvc;

    //If service contains dependency you can do the following
    // 1
    // @MockBean
    // private MyService service

    //If you want integration test you need to use the correct annotations
    //to fetch your service and dependencies
    //@SpringBootTest + @AutoconfigureMockMvc
    //@SpringBootTest + @AutoConfigureTestDatabase

    @Test
    void testHelloController() throws Exception {
        mockMvc.perform(get("/user/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello"))
                .andReturn();
    }

    @Test
    void testJsonReturnValues() throws Exception {
         mockMvc.perform(get("/user/example"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("ak"))
                .andExpect(jsonPath("$.password").value("pwd"));
    }
}
