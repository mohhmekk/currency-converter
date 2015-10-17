package org.sample.currency.app.service;

import org.sample.currency.app.dao.CurrencyRepository;
import org.sample.currency.app.model.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Business service for Local Currency-related operations.
 *
 * Created by Mohamed Mekkawy.
 */
@Service
public class CurrencyService {

    private final Logger logger = LoggerFactory.getLogger(CurrencyService.class);

    @Autowired
    private CurrencyRepository currencyRepository;

    /**
     * get all currencies.
     *
     * @return list of currencies
     */
    public List<Currency> getCurrencies() {
        logger.debug("getCurrencies()");

        List<Currency> currencies = currencyRepository.findAll();

        logger.debug("Returning list of {} currencies from database", currencies.size());
        return currencies;
    }
}
