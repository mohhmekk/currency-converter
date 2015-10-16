package org.sample.currency.app.service;


import org.junit.Test;
import org.sample.currency.app.AbstractSpringTests;
import org.sample.currency.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;


public class UserServiceTest extends AbstractSpringTests{

    public static final String USERNAME = "test123";

    @Autowired
    private UserService userService;

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void testFindUserByUsername() {
        User user = findUserByUsername(USERNAME);
        assertNotNull("User is mandatory", user);
        assertTrue("Unexpected user " + user.getUserName(), user.getUserName().equals(USERNAME));
    }

    @Test
    public void shouldReturnNullUser() {
        User user = findUserByUsername("doesnotexist");
        assertNull("User must be null", user);
    }

    @Test
    public void shouldCreateAndPersistValidUser() {
        userService.createUser("test456", "test@gmail.com", "Password3");
        User user = findUserByUsername("test456");

        assertTrue("username not expected " + user.getUserName(), "test456".equals(user.getUserName()));
        assertTrue("email not expected " + user.getEmail(), "test@gmail.com".equals(user.getEmail()));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        assertTrue("password not expected " + user.getPasswordDigest(), passwordEncoder.matches("Password3", user.getPasswordDigest()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForBlankUser() {
        userService.createUser("", "test@gmail.com", "Password3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForShortUserName() {
        userService.createUser("test", "test@gmail.com", "Password3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForExistedUserName() {
        userService.createUser("test123", "test@gmail.com", "Password3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForBlankMail() {
        userService.createUser("test001", "", "Password3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForInvalidMail() {
        userService.createUser("test001", "test", "Password3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForBlankPassword() {
        userService.createUser("test002", "test@gmail.com", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForInvalidPassword() {
        userService.createUser("test003", "test@gmail.com", "Password");
    }

    private User findUserByUsername(String username) {
        User user = mongoTemplate.findById(username, User.class);
        return user;
    }


}
