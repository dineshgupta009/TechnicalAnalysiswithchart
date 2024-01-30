package com.example.TechnicalAnalysis.controller;

import com.example.TechnicalAnalysis.entity.NSE;
import com.example.TechnicalAnalysis.feign.FeignClientNSEBankNifty;
import com.example.TechnicalAnalysis.feign.FeignClientNSENifty;
import com.example.TechnicalAnalysis.feign.FeignClientStocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.TechnicalAnalysis.entity.FilteredResponse;
import com.example.TechnicalAnalysis.feign.FeignBuilder;
import com.example.TechnicalAnalysis.processor.NSEDataProcessor;



@RestController
public class NseRestController {

    @Autowired
    FeignClientStocks stocks;
    @Autowired
    FeignClientNSEBankNifty bankNifty;

    @Autowired
    FeignClientNSENifty nifty;

    @Autowired
    NSEDataProcessor processor;

    @GetMapping("nifty")
    public FilteredResponse getNiftyData(Model model) {
        return processor.processNiftyData(nifty.getLiveNiftyData(FeignBuilder.builder()), 500, 50);
    }

    @GetMapping("banknifty")
    public FilteredResponse getBankNifty(Model model) {
        return processor.processBankNiftyData(bankNifty.getLiveBankNiftyData(FeignBuilder.builder()), 1000, 100);
    }


    @GetMapping("/stocks1")
    public NSE showAnalysisStocks1(@RequestParam (value = "symbol") String symbol ,Model model) {
//        NSE nse = stocks.getLiveStocksData(FeignBuilder.builder());
        NSE coalindia = stocks.getLiveCoalIndiaData(FeignBuilder.builder());
//        model.addAttribute("expiredate", nse.getRecords().getExpiryDates());
        return coalindia;
    }

    @GetMapping("/StocksData")
    public NSE showAnalysisStocks2(@RequestParam (value = "symbol") String symbol, Model model) {
//        NSE nse = stocks.getLiveStocksData(FeignBuilder.builder());
        NSE coalindia = stocks.getLiveCoalIndiaData(FeignBuilder.builder());
//        model.addAttribute("expiredate", nse.getRecords().getExpiryDates());
        return coalindia;
    }


}
