package org.sample.currency.app;

import org.junit.Before;
import org.sample.currency.config.servlet.ServletContextConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ContextConfiguration(classes = {ServletContextConfig.class})
@WebAppConfiguration
public abstract class AbstractSprinMVCTests extends AbstractSpringTests {

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
}
