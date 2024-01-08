package com.example.TechnicalAnalysis.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Filtered {
	Data data[];
	
	@JsonProperty("PE")
	PETotal pe;
	
	@JsonProperty("CE")
	CETotal ce;

	public Data[] getData() {
		return data;
	}

	public void setData(Data[] data) {
		this.data = data;
	}

	public PETotal getPe() {
		return pe;
	}

	public void setPe(PETotal pe) {
		this.pe = pe;
	}

	public CETotal getCe() {
		return ce;
	}

	public void setCe(CETotal ce) {
		this.ce = ce;
	}
	
	
}
