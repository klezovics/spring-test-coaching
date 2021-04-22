package com.klezovich.springtestcoaching.springboottest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//TODO Read this https://reflectoring.io/spring-boot-web-controller-test/
@WebMvcTest
public class UserControllerTest {

    @Autowired
    //Key player - mockMvc class
    //Allow you to ...
    //1) Create requests
    //2) Verify responses
    private MockMvc mockMvc;

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
