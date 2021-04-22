package com.klezovich.springtestcoaching.springboottest.repository;

import com.klezovich.springtestcoaching.entity.User;
import com.klezovich.springtestcoaching.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
//TODO: Read this https://reflectoring.io/spring-boot-data-jpa-test/
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    void testCanSaveUser() {
        var user = User.builder().username("ak").password("pwd").build();

        repository.save(user);
        assertNotNull(user.getId());
    }

    @Test
    void testCanFindUserById() {
        var user = User.builder().username("ak").password("pwd").build();

        repository.save(user);

        var userFromDb = repository.findById(user.getId());
        assertEquals(true,userFromDb.isPresent());
        assertEquals("ak",userFromDb.get().getUsername());
    }

    @Test
    void testCanFindUserByUsername() {
        var user = User.builder().username("ak").password("pwd").build();
        repository.save(user);

        var userFromDbOpt = repository.findByUsername("ak");
        assertEquals(true, userFromDbOpt.isPresent());

        assertEquals("pwd", userFromDbOpt.get().getPassword());
    }
}
