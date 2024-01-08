package com.example.TechnicalAnalysis.entity;

public class Records {

	String expiryDates[];
	
	String timestamp;
	
	double underlyingValue;
	
	Data data[];
	
	double strikePrices[];

	public Data[] getData() {
		return data;
	}

	public void setData(Data[] data) {
		this.data = data;
	}

	public double[] getStrikePrices() {
		return strikePrices;
	}

	public void setStrikePrices(double[] strikePrices) {
		this.strikePrices = strikePrices;
	}

	public String[] getExpiryDates() {
		return expiryDates;
	}

	public void setExpiryDates(String[] expiryDates) {
		this.expiryDates = expiryDates;
	}

	public double getUnderlyingValue() {
		return underlyingValue;
	}

	public void setUnderlyingValue(double underlyingValue) {
		this.underlyingValue = underlyingValue;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	

}
