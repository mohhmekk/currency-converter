package org.sample.currency.app.controller.currency;


import org.sample.currency.app.controller.currency.dto.CurrencyExchange;
import org.sample.currency.app.model.Currency;
import org.sample.currency.app.model.CurrencyExchangeHistory;
import org.sample.currency.app.service.CurrencyExchangeHistoryService;
import org.sample.currency.app.service.CurrencyService;
import org.sample.currency.external.CurrencyExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * REST service for currency for the currently logged in user.
 *
 * Created by Mohamed Mekkawy.
 */
@Controller
@RequestMapping("currency")
public class CurrencyController {

    private final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyExchangeHistoryService currencyExchangeHistoryService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "list")
    public List<Currency> getCurrencies() {
        logger.debug("getCurrencies()");

        List<Currency> result = currencyService.getCurrencies();

        return result;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "exchange")
    public CurrencyExchange getCurrencyExchange(@RequestParam(value = "currencyLeft") String currencyLeft,
                                                @RequestParam(value = "currencyRight") String currencyRight,
                                                @RequestParam(value = "date", required = false) Date date) {

        CurrencyExchange result = currencyExchangeService.getCurrencyExchange(currencyLeft, currencyRight, date);
        currencyExchangeHistoryService.saveExchangeHistory(result);

        return result;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "history")
    public List<CurrencyExchangeHistory> getCurrencyExchangeHistory() {

        return currencyExchangeHistoryService.getExchangeHistory();

    }

    /**
     * error handler for backend errors - a 400 status code will be sent back, and the body
     * of the message contains the exception text.
     *
     * @param exc - the exception caught
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        logger.error(exc.getMessage(), exc);
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
