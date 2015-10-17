package org.sample.currency.app.service;

import org.sample.currency.app.dao.UserRepository;
import org.sample.currency.app.model.User;
import org.sample.currency.app.util.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;


/**
 * Business service for User entity related operations
 *
 * Created by Mohamed Mekkawy.
 */
@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final Pattern PASSWORD_REGEX = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}");

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    @Autowired
    private UserRepository userRepository;

    /**
     * creates a new user in the database
     *
     * @param username - the username of the new user
     * @param email    - the user email
     * @param password - the user plain text password
     */
    public void createUser(String username, String email, String password) {

        logger.info("Creating new user with name {}", username);

        ValidationUtils.assertNotBlank(username, "Username cannot be empty.");
        ValidationUtils.assertMinimumLength(username, 6, "Username must have at least 6 characters.");
        ValidationUtils.assertNotBlank(email, "Email cannot be empty.");
        ValidationUtils.assertMatches(email, EMAIL_REGEX, "Invalid email.");
        ValidationUtils.assertNotBlank(password, "Password cannot be empty.");
        ValidationUtils.assertMatches(password, PASSWORD_REGEX, "Password must have at least 6 characters, with 1 numeric and 1 uppercase character.");

        if (userRepository.exists(username)) {
            logger.info("Username already exists, returning error");
            throw new IllegalArgumentException("The username is not available.");
        }

        User user = new User(username, new BCryptPasswordEncoder().encode(password), email);

        userRepository.save(user);

        logger.info("User with name {} created", username);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }

}
