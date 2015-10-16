package org.sample.currency.app.controller;


import org.junit.Before;
import org.junit.Test;
import org.sample.currency.app.AbstractSprinMVCTests;
import org.sample.currency.app.dao.UserRepository;
import org.sample.currency.app.model.User;
import org.sample.currency.app.service.UserServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sun.security.acl.PrincipalImpl;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends AbstractSprinMVCTests{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext wac;

    @Test
    public void testCreateUser() throws Exception {
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"testing123\", \"plainTextPassword\": \"Password5\", \"email\": \"test@gmail.com\"}")
                .accept(MediaType.APPLICATION_JSON)
                .principal(new PrincipalImpl(UserServiceTest.USERNAME)))
                .andDo(print())
                .andExpect(status().isOk());

        User user = userRepository.findByUserName("testing123");
        assertTrue("email not correct: " + user.getEmail(), "test@gmail.com".equals(user.getEmail()));
    }


}
