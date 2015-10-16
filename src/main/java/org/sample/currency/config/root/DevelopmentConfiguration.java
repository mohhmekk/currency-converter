package org.sample.currency.config.root;


import com.mongodb.MongoClient;
import org.sample.currency.app.util.TestDataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

import javax.annotation.PostConstruct;

/**
 * Development specific configuration - connects to Mongo database as configured in the properties file.
 * This profile is used if no other profiles activated.
 * <p/>
 * Set -Dspring.profiles.active=development to activate this config.
 *
 * Created by Mohamed Mekkawy.
 */
@Configuration
@Profile("development")
public class DevelopmentConfiguration {

    private final Logger logger = LoggerFactory.getLogger(DevelopmentConfiguration.class);

    /**
     * MongoDb configurations are read from property file.
     */
    @Value("${mongo.host}")
    String mongoHost;
    @Value("${mongo.port}")
    int mongoPort;
    @Value("${mongo.user}")
    String mongoUser;
    @Value("${mongo.pass}")
    String mongoPass;
    @Value("${mongo.db.name}")
    String dbName;

    @PostConstruct
    public void init(){
        logger.info("--------------------Initializing Development profile---------------------");
        logger.info("Mongo configurations: {}:{}/{} @{}/{}", mongoHost, mongoPort, dbName , mongoUser, mongoPass);
    }

    public
    @Bean
    MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(new MongoClient(mongoHost, mongoPort), dbName, new UserCredentials(mongoUser, mongoPass));
    }

    public
    @Bean
    MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());

    }

    /**
     * Insert list of currencies in the Currency collection if it is empty.
     */
    @Bean
    public TestDataManager initTestData() {
        return new TestDataManager();
    }


}
