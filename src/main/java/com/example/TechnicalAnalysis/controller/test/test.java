package com.example.TechnicalAnalysis.controller.test;

import com.example.TechnicalAnalysis.entity.Data;
import com.example.TechnicalAnalysis.entity.NSE;
import com.example.TechnicalAnalysis.feign.FeignBuilder;
import com.example.TechnicalAnalysis.feign.FeignClientNSENifty;
import com.example.TechnicalAnalysis.processor.NSEDataProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;


@Controller
public class test {

    @Autowired
    FeignClientNSENifty nifty;
//    @Autowired
//    NSEDataProcessor processor;

    @GetMapping("/chartStrikePrice")
    public String getStrikePrice(Model model) {
        List<Data> records;

        NSE nse = nifty.getLiveNiftyData(FeignBuilder.builder());

        Map<Double, Double> putVal = new LinkedHashMap<>();
        Map<Double, Double> callVal = new LinkedHashMap<>();

        Map<Double, Double> OiPutVal = new LinkedHashMap<>();
        Map<Double, Double> OiCallVal = new LinkedHashMap<>();

        double currentStrike = Math.round((nse.getRecords().getUnderlyingValue() / 50)) * 50;

        records = Arrays.asList(nse.getFiltered().getData()).stream()
                .filter(data -> (data.getStrikePrice() >= (currentStrike - 500) && data.getStrikePrice() <= (currentStrike + 500)))
                .collect(Collectors.toList());
        for (Data data : records) {
            putVal.put(data.getStrikePrice(), (data.getPe().getChangeinOpenInterest()));
            callVal.put(data.getStrikePrice(), (data.getCe().getChangeinOpenInterest()));
            OiPutVal.put(data.getStrikePrice(), (data.getPe().getOpenInterest()));
            OiCallVal.put(data.getStrikePrice(), (data.getCe().getOpenInterest()));
        }

        Set<Double> keySet = putVal.keySet();
        List<Double> strikePrice= new LinkedList<>(keySet);
        System.out.println(putVal.keySet());
        model.addAttribute("strikePrice", strikePrice);

        // Creating an ArrayList of values
        List<Double> listOfPutValues= new LinkedList<>(putVal.values());
        List<Double> listOfCallValues= new LinkedList<>(callVal.values());
//        System.out.println(putVal);
//        System.out.println(listOfPutValues);
        model.addAttribute("putVal", listOfPutValues);
        model.addAttribute("callVal", listOfCallValues);

        List<Double> listOfOiPutValues= new LinkedList<>(OiPutVal.values());
        List<Double> listOfOiCallValues= new LinkedList<>(OiCallVal.values());
        model.addAttribute("OiPutVal", listOfOiPutValues);
        model.addAttribute("OiCallVal", listOfOiCallValues);
        model.addAttribute("spotPrice",nse.getRecords().getUnderlyingValue());

        return "barChart";
    }

}
