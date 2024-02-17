package com.example.TechnicalAnalysis.entity.marketStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class MarketState {

    public String market;
    public String marketStatus;
    public String tradeDate;
    public String index;
    public Object last;
    public Object variation;
    public Object percentChange;
    public String marketStatusMessage;
    public String expiryDate;
    public String underlying;
    public String updated_time;
    public String tradeDateFormatted;
    public String slickclass;

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getMarketStatus() {
        return marketStatus;
    }

    public void setMarketStatus(String marketStatus) {
        this.marketStatus = marketStatus;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Object getLast() {
        return last;
    }

    public void setLast(Object last) {
        this.last = last;
    }

    public Object getVariation() {
        return variation;
    }

    public void setVariation(Object variation) {
        this.variation = variation;
    }

    public Object getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(Object percentChange) {
        this.percentChange = percentChange;
    }

    public String getMarketStatusMessage() {
        return marketStatusMessage;
    }

    public void setMarketStatusMessage(String marketStatusMessage) {
        this.marketStatusMessage = marketStatusMessage;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getUnderlying() {
        return underlying;
    }

    public void setUnderlying(String underlying) {
        this.underlying = underlying;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    public String getTradeDateFormatted() {
        return tradeDateFormatted;
    }

    public void setTradeDateFormatted(String tradeDateFormatted) {
        this.tradeDateFormatted = tradeDateFormatted;
    }

    public String getSlickclass() {
        return slickclass;
    }

    public void setSlickclass(String slickclass) {
        this.slickclass = slickclass;
    }
}
