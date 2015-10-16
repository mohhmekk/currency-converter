package org.sample.currency.app.dao;

import org.sample.currency.app.model.CurrencyExchangeHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Repository for the {@link CurrencyExchangeHistory}
 *
 * Created by Mohamed Mekkawy.
 */
public interface CurrencyExchangeHistoryRepository extends MongoRepository<CurrencyExchangeHistory, String>{

    @Override
    List<CurrencyExchangeHistory> findAll();

    List<CurrencyExchangeHistory> findByUserId(String userId);

}
