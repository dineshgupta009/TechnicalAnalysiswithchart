package com.example.TechnicalAnalysis.controller.view;


import com.example.TechnicalAnalysis.entity.FilteredResponse;
import com.example.TechnicalAnalysis.feign.FeignClientNSEBankNifty;
import com.example.TechnicalAnalysis.feign.FeignClientNSENifty;
import com.example.TechnicalAnalysis.feign.FeignClientStocks;
import com.example.TechnicalAnalysis.processor.NSEDataProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.TechnicalAnalysis.entity.NSE;
import com.example.TechnicalAnalysis.feign.FeignBuilder;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class DailyAnalysis {

    @Autowired
    FeignClientNSEBankNifty bankNifty;
    @Autowired
    FeignClientNSENifty nifty;

    @Autowired
    FeignClientStocks stocks;

    @Autowired
    NSEDataProcessor processor;

    @GetMapping("symbol")
    public String showAnalysis(Model model) {
        NSE nse = nifty.getLiveNiftyData(FeignBuilder.builder());
        model.addAttribute("expiredate", nse.getRecords().getExpiryDates());
        return "index";
    }

    @GetMapping("BankNiftyPage")
    public String showBankNiftyChart(Model model) {

        List<Double> showData = processor.getPutStrikePrice(bankNifty.getLiveBankNiftyData(FeignBuilder.builder()), 1000, 100);
        Collections.sort(showData);

        FilteredResponse response = processor.processNiftyData(nifty.getLiveNiftyData(FeignBuilder.builder()),500,50);

        System.out.println("Strike Price" +showData);

        List<Double> showData1 = processor.getPutVol(bankNifty.getLiveBankNiftyData(FeignBuilder.builder()), 1000, 100);
        Collections.sort(showData1);
        System.out.println("put vol"+showData1);

        List<Double> callVol = processor.getCallVol(bankNifty.getLiveBankNiftyData(FeignBuilder.builder()), 1000, 100);

        Collections.sort(callVol);
        List<Double> strikePrice = processor.getPutStrikePrice(bankNifty.getLiveBankNiftyData(FeignBuilder.builder()), 1000, 100);
        Collections.sort(strikePrice);
        List<Double> putVol = processor.getPutStrikePrice(bankNifty.getLiveBankNiftyData(FeignBuilder.builder()), 1000, 100);

        model.addAttribute("name", strikePrice);
        model.addAttribute("callVol", callVol);
        model.addAttribute("age", showData1);

        NSE nse = nifty.getLiveNiftyData(FeignBuilder.builder());
        model.addAttribute("spotPrice",nse.getRecords().getUnderlyingValue());
        model.addAttribute("expiryDate",nse.getRecords().getExpiryDates());


//        NSE nse = nifty.getLiveNiftyData(FeignBuilder.builder());
//        Double niftySpot = nse.getRecords().getUnderlyingValue();
//        System.out.println(niftySpot);
//        model.addAttribute("niftyValue", niftySpot);
        return "BankNiftyPage";

    }

    @GetMapping("NiftyPage")
    public String showNiftyChart(Model model) {

        List<Double> showData = processor.getPutStrikePrice(nifty.getLiveNiftyData(FeignBuilder.builder()), 500, 50);
        System.out.println("Strike Price" +showData);

        List<Double> showData1 = processor.getPutVol(nifty.getLiveNiftyData(FeignBuilder.builder()), 500, 50);
        System.out.println("put vol"+showData1);

        List<Double> callVol = processor.getCallVol(nifty.getLiveNiftyData(FeignBuilder.builder()), 500, 50);

        List<Double> strikePrice = processor.getPutStrikePrice(nifty.getLiveNiftyData(FeignBuilder.builder()), 500, 50);
//        List<Double> putVol = processor.getPutStrikePrice(nifty.getLiveNiftyData(FeignBuilder.builder()), 1000, 50);

        Collections.sort(strikePrice);
        NSE nse = nifty.getLiveNiftyData(FeignBuilder.builder());

        model.addAttribute("spotPrice", nse.getRecords().getUnderlyingValue());
        model.addAttribute("expiry",nse.getRecords().getExpiryDates());
        model.addAttribute("name", strikePrice);
        model.addAttribute("callVol", callVol);
        model.addAttribute("age", showData1);

        return "NiftyPage";

    }

    @GetMapping("/stocks")
    public String showAnalysisStocks1(Model model) {


        NSE nse = stocks.getLiveStocksData(FeignBuilder.builder());
        model.addAttribute("expiredate", nse.getRecords().getExpiryDates());
        return "index";
    }

}
