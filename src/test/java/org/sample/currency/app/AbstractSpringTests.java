package org.sample.currency.app;

import org.junit.runner.RunWith;
import org.sample.currency.config.root.RootContextConfig;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Base for all non-web test cases, initializes only spring root context.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {TestConfiguration.class, RootContextConfig.class})
public abstract class AbstractSpringTests {

}
