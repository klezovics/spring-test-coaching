package com.klezovich.springtestcoaching.controller;


import com.klezovich.springtestcoaching.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @RequestMapping("/example")
    public User getUser() {
        return User.builder().username("ak").password("pwd").build();
    }
}
