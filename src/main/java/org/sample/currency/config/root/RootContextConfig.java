package org.sample.currency.config.root;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.web.client.RestTemplate;


/**
 * The root context configuration of the application - the beans in this context will be globally visible
 * in all servlet contexts.
 *
 * Created by Mohamed Mekkawy.
 */

@Configuration
@ComponentScan({"org.sample.currency.app.service", "org.sample.currency.app.dao",
        "org.sample.currency.app.init", "org.sample.currency.app.security", "org.sample.currency.external"})
@PropertySource("classpath:app.properties")
@EnableMongoRepositories(basePackages = "org.sample.currency.app.dao")
public class RootContextConfig {

    /**
     * Ensures that placeholders are replaced with property values
     */
    @Bean
    static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
