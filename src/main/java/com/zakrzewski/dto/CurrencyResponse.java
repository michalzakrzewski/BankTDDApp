package com.zakrzewski.dto;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class CurrencyResponse {

    private String base;
    private LocalDate date;
    private Map<String, Double> rates;

    public CurrencyResponse(String base, LocalDate date, Map<String, Double> rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public CurrencyResponse() {
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyResponse that = (CurrencyResponse) o;
        return Objects.equals(base, that.base) && Objects.equals(date, that.date) && Objects.equals(rates, that.rates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, date, rates);
    }

    @Override
    public String toString() {
        return "CurrencyResponse{" +
                "base='" + base + '\'' +
                ", date='" + date + '\'' +
                ", rates=" + rates +
                '}';
    }
}
