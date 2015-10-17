package org.sample.currency.app.controller;


import org.junit.Before;
import org.junit.Test;
import org.sample.currency.app.AbstractSprinMVCTests;
import org.sample.currency.app.controller.currency.CurrencyController;
import org.sample.currency.app.model.Currency;
import org.sample.currency.app.service.UserServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sun.security.acl.PrincipalImpl;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for {@link CurrencyController} which includes testing down layers
 * of service and DAO, All of external calls are being mocked.
 */
public class CurrencyControllerTest extends AbstractSprinMVCTests{

    /**
     * Testing the rest API /currency/list
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnTwoCurrencies() throws Exception {
        mockMvc.perform(get("/currency/list")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .principal(new PrincipalImpl(UserServiceTest.USERNAME)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("United Arab Emirates Dirham")))
                .andExpect(jsonPath("$[0].shortName", is("AED")))
                .andExpect(jsonPath("$[1].name", is("United States Dollar")))
                .andExpect(jsonPath("$[1].shortName", is("USD")))
                .andReturn();
    }


}
