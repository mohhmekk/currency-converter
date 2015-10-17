package org.sample.currency.app.service.external;

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
import java.text.ParseException;
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

    private final Logger logger = LoggerFactory.getLogger(CurrencyExchangeService.class);

    @Autowired
    private CurrencyRepository currencyRepository;

    /**
     * get list of available currencies from the service API configured.
     *
     * @return list of currencies
     */
    public List<Currency> getCurrencies() {
        logger.info("Getting list of latest currencies from service url {}, this is a time consuming operation please try to avoid"
        , currenciesServiceUrl);

        Map<String, String> rawCurrencies = callAndValidateGetService(UriComponentsBuilder.fromHttpUrl(currenciesServiceUrl).build().toUri());

        List<Currency> currencies = rawCurrencies.entrySet().stream().map(stringStringEntry -> new Currency(stringStringEntry.getKey(), stringStringEntry.getValue())).collect(Collectors.toList());

        logger.info("Returning list of {} currencies", currencies.size());
        return currencies;
    }

    /**
     * Get currency exchange rate from the service API configured.
     *
     * TODO Request and Response parameter names need to be externally managed and configured according to the used service API
     */
    public CurrencyExchange getCurrencyExchange(String currencyLeftId, String currencyRightId, Date date) {

        logger.debug("getCurrencyExchange({}, {}, {})", currencyLeftId, currencyRightId, date);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Currency currencyLeft = currencyRepository.findOne(currencyLeftId);
        Currency currencyRight = currencyRepository.findOne(currencyRightId);

        URI targetUrl = null;
        CurrencyExchange currencyExchange = new CurrencyExchange();

        //switch between live and historical data according to the passed date.
        if(Utils.beforeToday(date)){
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
                    .queryParam("currencies", currencyLeft.getShortName() + "," + currencyRight.getShortName())
                    .build()
                    .toUri();
        }

        Map<String, Object> responseParameters = callAndValidateGetService(targetUrl);

        Map<String, Number> rates = (Map<String, Number>) responseParameters.get("quotes");
        logger.debug("Exchange rates received: {}", rates);
        //TODO avoid using plain strings (like USD) to get parameter values from response.
        BigDecimal  rate  = new BigDecimal(rates.get("USD"+currencyRight.getShortName()).doubleValue() / rates.get("USD"+currencyLeft.getShortName()).doubleValue());
        BigDecimal inverseRate= new BigDecimal(rates.get("USD"+currencyLeft.getShortName()).doubleValue() / rates.get("USD"+currencyRight.getShortName()).doubleValue());
        Date exchangeDate = new Date();
        try {
            exchangeDate = responseParameters.get("date") == null ? new Date() : dateFormat.parse(responseParameters.get("date").toString());
        } catch (ParseException e) {
            logger.error("Error parsing date {} , exchange date will be left to default", responseParameters.get("date"));
        }

        currencyExchange.setLeftCurrency(currencyLeft);
        currencyExchange.setRightCurrency(currencyRight);
        currencyExchange.setValue(rate);
        currencyExchange.setInverseValue(inverseRate);
        currencyExchange.setExchangeDate(exchangeDate);

        return currencyExchange;

    }

    /**
     * Makes Get request to a URI, Validates response if it is successful.
     *
     * @return map of raw returned parameters.
     */
    private Map callAndValidateGetService(URI targetUrl) {
        logger.debug("Initiating Get request {}", targetUrl);
        Map responseParameters = restTemplate.getForObject(targetUrl, Map.class);
        logger.debug("Received parameters: {}", responseParameters);
        checkSuccessfulResponse(responseParameters);
        return responseParameters;
    }

    /**
     * If response is a failure then throw exception with error message.
     * @param rawParameters
     */
    private void checkSuccessfulResponse(Map rawParameters) {
        if(rawParameters.containsKey("success")) {
            if (!(Boolean) rawParameters.get("success")) {
                throw new ExternalServiceException("Error calling service " + currenciesServiceUrl + ", error " + rawParameters.get("error"));
            }
        }
    }


}
