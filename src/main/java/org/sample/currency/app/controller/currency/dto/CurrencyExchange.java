package org.sample.currency.app.controller.currency.dto;


import org.sample.currency.app.model.Currency;

import java.math.BigDecimal;
import java.util.Date;

/**
 * JSON-serializable DTO containing for Currency exchange
 *
 * Created by Mohamed Mekkawy.
 */
public class CurrencyExchange {

    private Currency leftCurrency;

    private Currency rightCurrency;

    private BigDecimal value;

    private BigDecimal inverseValue;

    private Date exchangeDate;

    public CurrencyExchange() {

    }

    public CurrencyExchange(Currency leftCurrency, Currency rightCurrency,
                            BigDecimal value, BigDecimal inverseValue) {
        super();
        this.leftCurrency = leftCurrency;
        this.rightCurrency = rightCurrency;
        this.value = value;
        this.inverseValue = inverseValue;
    }

    public Date getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(Date exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    public Currency getLeftCurrency() {
        return leftCurrency;
    }

    public void setLeftCurrency(Currency leftCurrency) {
        this.leftCurrency = leftCurrency;
    }

    public Currency getRightCurrency() {
        return rightCurrency;
    }

    public void setRightCurrency(Currency rightCurrency) {
        this.rightCurrency = rightCurrency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getInverseValue() {
        return inverseValue;
    }

    public void setInverseValue(BigDecimal inverseValue) {
        this.inverseValue = inverseValue;
    }
}
