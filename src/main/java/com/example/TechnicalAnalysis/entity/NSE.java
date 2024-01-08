package com.example.TechnicalAnalysis.entity;

import org.springframework.stereotype.Component;

@Component
public class NSE {
	
Records records;

Filtered filtered;

public Records getRecords() {
	return records;
}

public void setRecords(Records records) {
	this.records = records;
}

public Filtered getFiltered() {
	return filtered;
}

public void setFiltered(Filtered filtered) {
	this.filtered = filtered;
}



}
