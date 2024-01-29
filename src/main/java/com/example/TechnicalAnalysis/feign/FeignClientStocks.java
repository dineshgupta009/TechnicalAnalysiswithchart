package com.example.TechnicalAnalysis.feign;

import com.example.TechnicalAnalysis.entity.NSE;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;
import java.util.Map;

@FeignClient(name = "nsestockservice", url = "https://www.nseindia.com")
@Component
public interface FeignClientStocks {

    //    @GetMapping("/api/option-chain-indices?symbol=NIFTY") api/option-chain-equities?symbol=ATUL
//    https://www.nseindia.com/api/option-chain-equities?symbol=APOLLOHOSP
//    @GetMapping("/api/option-chain-equities?symbol=APOLLOHOSP")

    @GetMapping("/api/option-chain-equities?symbol=ATUL")
    public NSE getLiveStocksData(@RequestHeader Map<String, String> headerMap1);

//    COALINDIA

    @GetMapping("/api/option-chain-equities?symbol=COALINDIA")
    public NSE getLiveCoalIndiaData(@RequestHeader Map<String, String> headerMap1);

}
