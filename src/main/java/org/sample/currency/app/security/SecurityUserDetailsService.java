package org.sample.currency.app.security;


import org.sample.currency.app.dao.UserRepository;
import org.sample.currency.app.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * UserDetails service that reads the user credentials from the database.
 *
 * Created by Mohamed Mekkawy.
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(SecurityUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);

        if (user == null) {
            String message = "Username not found" + username;
            logger.info(message);
            throw new UsernameNotFoundException(message);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        logger.info("Found user {} in database: ", user);

        return new org.springframework.security.core.userdetails.User(username, user.getPasswordDigest(), authorities);
    }
}
