package com.example.TechnicalAnalysis.feign;

import com.example.TechnicalAnalysis.entity.NSE;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


import java.util.Map;

@FeignClient(name = "nsestockservice", url = "https://www.nseindia.com")
@Component
public interface FeignClientStocks {


    @GetMapping("/api/option-chain-equities?symbol=ATUL")
    public NSE getLiveStocksData(@RequestHeader Map<String, String> headerMap1);

    @GetMapping("/api/option-chain-equities?symbol=COALINDIA")
    public NSE getLiveCoalIndiaData(@RequestHeader Map<String, String> headerMap1);

    @GetMapping("/api/option-chain-equities?symbol=AARTIIND")
    public NSE getLiveAartiIndData(@RequestHeader Map<String, String> headerMap1);

    @GetMapping("/api/option-chain-equities?symbol=ABBOTINDIA")
    public NSE getLiveABBOTINDIAData(@RequestHeader Map<String, String> headerMap1);

    @GetMapping("/api/option-chain-equities?symbol=ABCAPITAL")
    public NSE getLiveABCAPITALData(@RequestHeader Map<String, String> headerMap1);

    @GetMapping("/api/option-chain-equities?symbol=ABFRL")
    public NSE getLiveABFRLData(@RequestHeader Map<String, String> headerMap1);

    @GetMapping("/api/option-chain-equities?symbol=ACC")
    public NSE getLiveACCData(@RequestHeader Map<String, String> headerMap1);

    @GetMapping("/api/option-chain-equities?symbol=ADANIENT")
    public NSE getLiveADANIENTData(@RequestHeader Map<String, String> headerMap1);

    @GetMapping("/api/option-chain-equities?symbol=ADANIPORTS")
    public NSE getLiveADANIPORTSData(@RequestHeader Map<String, String> headerMap1);

    @GetMapping("/api/option-chain-equities?symbol=ALKEM")
    public NSE getLiveALKEMData(@RequestHeader Map<String, String> headerMap1);

    @GetMapping("/api/option-chain-equities?symbol=AMBUJACEM")
    public NSE getLiveAMBUJACEMData(@RequestHeader Map<String, String> headerMap1);

    @GetMapping("/api/option-chain-equities?symbol=APOLLOHOSP")
    public NSE getLiveAPOLLOHOSPData(@RequestHeader Map<String, String> headerMap1);

    @GetMapping("/api/option-chain-equities?symbol=APOLLOTYRE")
    public NSE getLiveAPOLLOTYREData(@RequestHeader Map<String, String> headerMap1);

    @GetMapping("/api/option-chain-equities?symbol=ASHOKLEY")
    public NSE getLiveASHOKLEYData(@RequestHeader Map<String, String> headerMap1);

    @GetMapping("/api/option-chain-equities?symbol=ASIANPAINT")
    public NSE getLiveASIANPAINTData(@RequestHeader Map<String, String> headerMap1);

    @GetMapping("/api/option-chain-equities?symbol=ASTRAL")
    public NSE getLiveASTRALData(@RequestHeader Map<String, String> headerMap1);

    @GetMapping("/api/option-chain-equities?symbol=ATUL")
    public NSE getLiveATULData(@RequestHeader Map<String, String> headerMap1);

    @GetMapping("/api/option-chain-equities?symbol=AUROPHARMA")
    public NSE getLiveAUROPHARMAData(@RequestHeader Map<String, String> headerMap1);

    @GetMapping("/api/option-chain-equities?symbol=AXISBANK")
    public NSE getLiveAXISBANKData(@RequestHeader Map<String, String> headerMap1);

    @GetMapping("/api/option-chain-equities?symbol=BAJAJ-AUTO")
    public NSE getLiveBAJAJAUTOData(@RequestHeader Map<String, String> headerMap1);

    @GetMapping("/api/option-chain-equities?symbol=BAJAJFINSV")
    public NSE getLiveBAJAJFINSVData(@RequestHeader Map<String, String> headerMap1);

    @GetMapping("/api/option-chain-equities?symbol=BAJFINANCE")
    public NSE getLiveBAJFINANCEData(@RequestHeader Map<String, String> headerMap1);

    @GetMapping("/api/option-chain-equities?symbol=ABB")
    public NSE getLiveABBData(@RequestHeader Map<String, String> headerMap1);

}
