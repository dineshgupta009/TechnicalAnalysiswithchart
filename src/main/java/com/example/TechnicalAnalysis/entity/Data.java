package com.example.TechnicalAnalysis.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

	double strikePrice;
	
	String expiryDate;
	
	@JsonProperty("PE")
	PE pe;
	
	@JsonProperty("CE")
	CE ce;
		
	public PE getPe() {
		return pe;
	}
	public void setPe(PE pe) {
		this.pe = pe;
	}
	public CE getCe() {
		return ce;
	}
	public void setCe(CE ce) {
		this.ce = ce;
	}
	public double getStrikePrice() {
		return strikePrice;
	}
	public void setStrikePrice(double strikePrice) {
		this.strikePrice = strikePrice;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	
	
}


