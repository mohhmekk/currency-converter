package org.sample.currency.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

/**
 * History of visited exchange rates, to be grouped by user id.
 *
 * Created by Mohamed Mekkawy.
 */
@Document
public class CurrencyExchangeHistory {

    @Id
    private String id;
    @DBRef
    private Currency fromCurrency;
    @DBRef
    private Currency toCurrency;
    private BigDecimal value;
    private String userId;

    private Date exchangeDate;
    private Date currentDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(Currency toCurrency) {
        this.toCurrency = toCurrency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(Date exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public String toString() {
        return "CurrencyExchangeHistory{" +
                "id='" + id + '\'' +
                ", fromCurrency=" + fromCurrency +
                ", toCurrency=" + toCurrency +
                ", value=" + value +
                ", userId='" + userId + '\'' +
                ", exchangeDate=" + exchangeDate +
                ", currentDate=" + currentDate +
                '}';
    }
}
