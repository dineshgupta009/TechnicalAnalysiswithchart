package com.example.TechnicalAnalysis.feign;

import com.example.TechnicalAnalysis.entity.NSE;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name="FinNiftyService", url = "https://www.nseindia.com")
@Component
public interface FeignClientNSEFinNifty {
	
	@GetMapping("/api/option-chain-indices?symbol=FINNIFTY")
	public NSE 	getLiveFinNiftyData(@RequestHeader Map<String, String> headerMap);
}
