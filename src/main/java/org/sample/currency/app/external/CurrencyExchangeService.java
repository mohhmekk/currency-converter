package org.sample.currency.app.external;

import org.sample.currency.app.controller.currency.dto.CurrencyExchange;
import org.sample.currency.app.dao.CurrencyRepository;
import org.sample.currency.app.model.Currency;
import org.sample.currency.app.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Proxy Business service for external Currency-related operations.
 */
@Service
public class CurrencyExchangeService {

    @Autowired
    protected RestTemplate restTemplate;

    //list of currencies service.
    @Value("${currencies.service.url}")
    protected String currenciesServiceUrl;

    //live converter service
    @Value("${exchange.live.service.url}")
    protected String liveExchangeUrl;

    //historical converter service
    @Value("${exchange.historical.service.url}")
    protected String historicalExchangeUrl;

    @Value("${access.key}")
    private String accessKey;

    private DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");

    private final Logger logger = LoggerFactory.getLogger(CurrencyExchangeService.class);

    @Autowired
    private CurrencyRepository currencyRepository;

    /**
     * get list of available currencies.
     *
     * @return list of currencies
     */
    public List<Currency> getCurrencies() {
        logger.info("Getting list of latest currencies from service url {}, this is a time consuming operation please try to avoid"
        , currenciesServiceUrl);

        Map<String, String> rawCurrencies = restTemplate.getForObject(currenciesServiceUrl, Map.class);

        checkSuccessfulResponse(rawCurrencies);

        List<Currency> currencies = rawCurrencies.entrySet().stream().map(stringStringEntry -> {
            return new Currency(stringStringEntry.getKey(), stringStringEntry.getValue());
        }).collect(Collectors.toList());

        logger.info("Returning list of {} currencies", currencies.size());
        return currencies;
    }

    /**
     * get the currency exchange.
     */
    public CurrencyExchange getCurrencyExchange(String currencyLeftId, String currencyRightId, Date date) {

        Currency currencyLeft = currencyRepository.findOne(currencyLeftId);
        Currency currencyRight = currencyRepository.findOne(currencyRightId);

        URI targetUrl = null;
        CurrencyExchange currencyExchange = new CurrencyExchange();

        if(Utils.historicalDataRequested(date)){
            currencyExchange.setExchangeDate(date);
            targetUrl =  UriComponentsBuilder.fromUriString(historicalExchangeUrl)
                    .queryParam("access_key", accessKey)
                    .queryParam("date", dateFormat.format(date))
                    .queryParam("currencies", currencyLeft.getShortName()+","+currencyRight.getShortName())
                    .build()
                    .toUri();
        }else{
            currencyExchange.setExchangeDate(new Date());
            targetUrl = UriComponentsBuilder.fromUriString(liveExchangeUrl)
                    .queryParam("access_key", accessKey)
                    .queryParam("currencies", currencyLeft.getShortName()+","+currencyRight.getShortName())
                    .build()
                    .toUri();
        }

        Map<String, Object> responseParameters = restTemplate.getForObject(targetUrl, Map.class);

        checkSuccessfulResponse(responseParameters);

        Map<String, Double> rates = (Map<String, Double>) responseParameters.get("quotes");

        BigDecimal  rate  = new BigDecimal(rates.get("USD"+currencyRight.getShortName()) / rates.get("USD"+currencyLeft.getShortName()));
        BigDecimal inverseRate= new BigDecimal(rates.get("USD"+currencyLeft.getShortName()) / rates.get("USD"+currencyRight.getShortName()));

        currencyExchange.setLeftCurrency(currencyLeft);
        currencyExchange.setRightCurrency(currencyRight);
        currencyExchange.setValue(rate);
        currencyExchange.setInverseValue(inverseRate);

        return currencyExchange;

    }

    /**
     * If response is a failure, throw exception with error message.
     * @param responseParameters
     */
    private void checkSuccessfulResponse(Map responseParameters) {
        if(responseParameters.containsKey("success")) {
            if (!(Boolean) responseParameters.get("success")) {
                throw new ExternalServiceException("Error calling service " + currenciesServiceUrl + ", error " + responseParameters.get("error"));
            }
        }
    }


}
