package org.sample.currency.app;


import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

/**
 * Integration testing specific configuration - creates a in-memory mongo database,
 */
@Configuration
@Profile("test")
public class TestConfiguration {

    @Value("${mongo.db.name}")
    String dbName;

    /**
     * In-memory mongo database.
     */
    @Bean
    public Mongo mongo() throws Exception {
        return new Fongo(dbName).getMongo();
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(new SimpleMongoDbFactory(mongo(), "test"));
    }

    /**
     * Populate collections with test data.
     *
     */
    @Bean
    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {
        Jackson2RepositoryPopulatorFactoryBean factoryBean = new Jackson2RepositoryPopulatorFactoryBean();
        factoryBean.setResources(new Resource[]{new ClassPathResource("test-data.json")});
        return factoryBean;
    }
}
