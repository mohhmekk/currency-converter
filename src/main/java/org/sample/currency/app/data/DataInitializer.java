package org.sample.currency.app.data;


import org.sample.currency.app.dao.CurrencyRepository;
import org.sample.currency.app.model.Currency;
import org.sample.currency.app.model.CurrencyExchangeHistory;
import org.sample.currency.app.service.external.CurrencyExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * This is an initializing bean that, Used in development profile:
 *  1- Insert currencies in the DB IFF the Currencies collection is empty.
 *  2- Insert sample User account test123/Password1 in User collection.
 *  3- Clean up the history collection after execution.
 *
 * Created by Mohamed Mekkawy.
 */
@Component
public class DataInitializer {

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final Logger logger = LoggerFactory.getLogger(DataInitializer.class);


    /**
     * Populate currencies collection if it is empty.
     *
     */
    @PostConstruct
    public void init() throws Exception {
        if(currencyRepository.findAll().size() == 0) {
            logger.info("Currency collection is empty, filling it with fresh currencies.");
            List<Currency> currencyList = currencyExchangeService.getCurrencies();
            currencyRepository.save(currencyList);
        }

    }

    /**
     * Populate User collection with a sample data user account test123/Password1.
     *
     */
    @Bean
    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {
        logger.info("Inserting some initial data");

        Jackson2RepositoryPopulatorFactoryBean factoryBean = new Jackson2RepositoryPopulatorFactoryBean();
        factoryBean.setResources(new Resource[]{new ClassPathResource("init-data.json")});
        return factoryBean;
    }

    /**
     * Clean up after execution by dropping used test db instance.
     *
     * @throws Exception
     */
    @PreDestroy
    void dropTestData() throws Exception {
        logger.info("Clean up test data");
        mongoTemplate.dropCollection(CurrencyExchangeHistory.class);
    }
}
