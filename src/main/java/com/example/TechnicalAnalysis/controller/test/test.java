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

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;


@Controller
public class test {

    @Autowired
    FeignClientNSENifty nifty;
    @Autowired
    NSEDataProcessor processor;

    @GetMapping("/chartStrikePrice")
    public String getStrikePrice(Model model) {
        List<Data> records;


        NSE nse = nifty.getLiveNiftyData(FeignBuilder.builder());

//        spotprice=nse.getRecords().getUnderlyingValue();
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

        List<String> operators =  new ArrayList<>();
        operators.add("NIFTY");
        operators.add("BANKNIFTY");
        model.addAttribute("operators", operators);

//      getting pcr value

       Double putValue =  processor.processNiftyData(nifty.getLiveNiftyData(FeignBuilder.builder()),500,50).getPutTotalOI();
        Double callValue =  processor.processNiftyData(nifty.getLiveNiftyData(FeignBuilder.builder()),500,50).getCallTotalOI();

        float pcr = Float.parseFloat(String.format("%.2f", (putValue/callValue)));

//        String.format("%.2f", pcr);
        System.out.println(pcr);
        model.addAttribute("pcr" ,pcr);

        return "barChart";
    }

    @GetMapping("/builder")
    public String builderPage(){
        return "builder";
    }

    @GetMapping("/test3")
    public String test(){
        return "test3";
    }
}
