package com.example.TechnicalAnalysis.entity.marketStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class Marketcap{

    public String timeStamp;
    public double marketCapinTRDollars;
    public double marketCapinLACCRRupees;
    public double marketCapinCRRupees;
    public String marketCapinCRRupeesFormatted;
    public String marketCapinLACCRRupeesFormatted;
    public String underlying;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getMarketCapinTRDollars() {
        return marketCapinTRDollars;
    }

    public void setMarketCapinTRDollars(double marketCapinTRDollars) {
        this.marketCapinTRDollars = marketCapinTRDollars;
    }

    public double getMarketCapinLACCRRupees() {
        return marketCapinLACCRRupees;
    }

    public void setMarketCapinLACCRRupees(double marketCapinLACCRRupees) {
        this.marketCapinLACCRRupees = marketCapinLACCRRupees;
    }

    public double getMarketCapinCRRupees() {
        return marketCapinCRRupees;
    }

    public void setMarketCapinCRRupees(double marketCapinCRRupees) {
        this.marketCapinCRRupees = marketCapinCRRupees;
    }

    public String getMarketCapinCRRupeesFormatted() {
        return marketCapinCRRupeesFormatted;
    }

    public void setMarketCapinCRRupeesFormatted(String marketCapinCRRupeesFormatted) {
        this.marketCapinCRRupeesFormatted = marketCapinCRRupeesFormatted;
    }

    public String getMarketCapinLACCRRupeesFormatted() {
        return marketCapinLACCRRupeesFormatted;
    }

    public void setMarketCapinLACCRRupeesFormatted(String marketCapinLACCRRupeesFormatted) {
        this.marketCapinLACCRRupeesFormatted = marketCapinLACCRRupeesFormatted;
    }

    public String getUnderlying() {
        return underlying;
    }

    public void setUnderlying(String underlying) {
        this.underlying = underlying;
    }
}
