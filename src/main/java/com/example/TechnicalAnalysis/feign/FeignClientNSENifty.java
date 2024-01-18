package com.example.TechnicalAnalysis.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.TechnicalAnalysis.entity.NSE;

@FeignClient(name="nseservice", url = "https://www.nseindia.com")
@Component
public interface FeignClientNSENifty {
	
	@GetMapping("/api/option-chain-indices?symbol=NIFTY")
	public NSE 	getLiveNiftyData(@RequestHeader Map<String, String> headerMap);

	@GetMapping("/api/option-chain-indices?symbol=MIDCPNIFTY")
	public NSE 	getLiveMIDCPNiftyData(@RequestHeader Map<String, String> headerMap);
}
