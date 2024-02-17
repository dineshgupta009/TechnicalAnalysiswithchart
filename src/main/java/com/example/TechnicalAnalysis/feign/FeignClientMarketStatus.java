package com.example.TechnicalAnalysis.feign;

import com.example.TechnicalAnalysis.entity.NSE;
import com.example.TechnicalAnalysis.entity.marketStatus.Status;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "nseMarketStatus", url = "https://www.nseindia.com")
@Component
public interface FeignClientMarketStatus {

//   https://www.nseindia.com/api/marketStatus
    @GetMapping("/api/marketStatus")
    public Status getMarketStatusData(@RequestHeader Map<String, String> headerMap1);

}
