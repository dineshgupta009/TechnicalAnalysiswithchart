package com.example.TechnicalAnalysis.entity.marketStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


public class Status {

    public ArrayList<MarketState> marketState;
    public Marketcap marketcap;
    public Indicativenifty50 indicativenifty50;

    public ArrayList<MarketState> getMarketState() {
        return marketState;
    }

    public void setMarketState(ArrayList<MarketState> marketState) {
        this.marketState = marketState;
    }

    public Marketcap getMarketcap() {
        return marketcap;
    }

    public void setMarketcap(Marketcap marketcap) {
        this.marketcap = marketcap;
    }

    public Indicativenifty50 getIndicativenifty50() {
        return indicativenifty50;
    }

    public void setIndicativenifty50(Indicativenifty50 indicativenifty50) {
        this.indicativenifty50 = indicativenifty50;
    }
}
