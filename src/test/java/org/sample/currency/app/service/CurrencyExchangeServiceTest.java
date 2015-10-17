package org.sample.currency.app.service;


import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.sample.currency.app.AbstractSpringTests;
import org.sample.currency.app.controller.currency.dto.CurrencyExchange;
import org.sample.currency.app.service.external.CurrencyExchangeService;
import org.sample.currency.app.service.external.ExternalServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.Assert.*;


public class CurrencyExchangeServiceTest extends AbstractSpringTests{

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Value("${exchange.live.service.url}")
    private String exchangeUrl;

    @Before
    public void setUp() throws Exception {
        mockServer = MockRestServiceServer.createServer(restTemplate);

    }

    @Test
    public void shouldReturnCorrectRate() {
        final double USDAED = 4.75;
        final double USDEGP = 7.84;
        mockServer.expect(MockRestRequestMatchers.requestTo(CoreMatchers.any(String.class))).andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess("{\n" +
                        "  \"success\":true,\n" +
                        "  \"source\":\"USD\",\n" +
                        "  \"quotes\":{\n" +
                        "    \"USDAED\":" + USDAED + ",\n" +
                        "    \"USDEGP\":" + USDEGP + " }}", MediaType.APPLICATION_JSON));

        CurrencyExchange currencyExchange = currencyExchangeService.getCurrencyExchange("AED", "EGP", null);
        assertEquals(currencyExchange.getValue(), new BigDecimal(USDEGP/USDAED));
        assertEquals(currencyExchange.getLeftCurrency().getShortName(), "AED");
        assertEquals(currencyExchange.getRightCurrency().getShortName(), "EGP");
    }

    @Test
    public void shouldReturnCorrectRateForIntegerValues() {
        final double USDAED = 4.75;
        final double USDEGP = 112;
        mockServer.expect(MockRestRequestMatchers.requestTo(CoreMatchers.any(String.class))).andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess("{\n" +
                        "  \"success\":true,\n" +
                        "  \"source\":\"USD\",\n" +
                        "  \"quotes\":{\n" +
                        "    \"USDAED\":" + USDAED + ",\n" +
                        "    \"USDEGP\":" + USDEGP + " }}", MediaType.APPLICATION_JSON));

        CurrencyExchange currencyExchange = currencyExchangeService.getCurrencyExchange("AED", "EGP", null);
        assertEquals(currencyExchange.getValue(), new BigDecimal(USDEGP/USDAED));
        assertEquals(currencyExchange.getLeftCurrency().getShortName(), "AED");
        assertEquals(currencyExchange.getRightCurrency().getShortName(), "EGP");
    }

    @Test(expected = ExternalServiceException.class)
    public void shouldThrowExceptionForFailureResponse() {
        mockServer.expect(MockRestRequestMatchers.requestTo(CoreMatchers.any(String.class))).andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess("{\n" +
                        "  \"success\":false}", MediaType.APPLICATION_JSON));

        currencyExchangeService.getCurrencyExchange("AED", "EGP", null);
    }
}
