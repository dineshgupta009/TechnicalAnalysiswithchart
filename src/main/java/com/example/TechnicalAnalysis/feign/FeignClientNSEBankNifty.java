package com.example.TechnicalAnalysis.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.TechnicalAnalysis.entity.NSE;

@FeignClient(name="nsebankniftyservice", url = "https://www.nseindia.com")
@Component
public interface FeignClientNSEBankNifty {
	
	@GetMapping("/api/option-chain-indices?symbol=BANKNIFTY")
	public NSE getLiveBankNiftyData(@RequestHeader Map<String, String> headerMap);

}
