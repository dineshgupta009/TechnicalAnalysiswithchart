package com.example.TechnicalAnalysis.entity.marketStatus;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class Indicativenifty50 {

    public String dateTime;
    public String indexName;
    public double closingValue;
    public int finalClosingValue;
    public double change;
    public double perChange;
    public String status;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public double getClosingValue() {
        return closingValue;
    }

    public void setClosingValue(double closingValue) {
        this.closingValue = closingValue;
    }

    public int getFinalClosingValue() {
        return finalClosingValue;
    }

    public void setFinalClosingValue(int finalClosingValue) {
        this.finalClosingValue = finalClosingValue;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getPerChange() {
        return perChange;
    }

    public void setPerChange(double perChange) {
        this.perChange = perChange;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
