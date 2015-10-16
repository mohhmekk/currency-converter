package org.sample.currency.app.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The Currency Document
 */
@Document
public class Currency {


    @Id
    private String shortName;
    private String symbol;
    private String name;

    public Currency() {

    }

    public Currency(String name, String shortName, String symbol) {
        super();
        this.name = name;
        this.shortName = shortName;
        this.symbol = symbol;
    }

    public Currency(String shortName, String name) {
        this.shortName = shortName;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
