package com.example.TechnicalAnalysis.controller.view;


import com.example.TechnicalAnalysis.entity.Data;
import com.example.TechnicalAnalysis.entity.FilteredResponse;
import com.example.TechnicalAnalysis.feign.*;
import com.example.TechnicalAnalysis.processor.NSEDataProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.TechnicalAnalysis.entity.NSE;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class DailyAnalysis {

    @Autowired
    FeignClientNSEBankNifty bankNifty;
    @Autowired
    FeignClientNSENifty nifty;

    @Autowired
    FeignClientNSEFinNifty finNifty;

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
        List<Data> records;

        NSE nse = bankNifty.getLiveBankNiftyData(FeignBuilder.builder());

        Map<Double, Double> putVal = new LinkedHashMap<>();
        Map<Double, Double> callVal = new LinkedHashMap<>();

        Map<Double, Double> OiPutVal = new LinkedHashMap<>();
        Map<Double, Double> OiCallVal = new LinkedHashMap<>();

        double currentStrike = Math.round((nse.getRecords().getUnderlyingValue() / 100)) * 100;

        records = Arrays.asList(nse.getFiltered().getData()).stream()
                .filter(data -> (data.getStrikePrice() >= (currentStrike - 1000) && data.getStrikePrice() <= (currentStrike + 1000)))
                .collect(Collectors.toList());
        for (Data data : records) {
            putVal.put(data.getStrikePrice(), (data.getPe().getChangeinOpenInterest()));
            callVal.put(data.getStrikePrice(), (data.getCe().getChangeinOpenInterest()));
            OiPutVal.put(data.getStrikePrice(), (data.getPe().getOpenInterest()));
            OiCallVal.put(data.getStrikePrice(), (data.getCe().getOpenInterest()));
        }

        Set<Double> keySet = putVal.keySet();
        List<Double> strikePrice = new LinkedList<>(keySet);
        System.out.println(putVal.keySet());
        model.addAttribute("strikePrice", strikePrice);

        // Creating an ArrayList of values
        List<Double> listOfPutValues = new LinkedList<>(putVal.values());
        List<Double> listOfCallValues = new LinkedList<>(callVal.values());
//        System.out.println(putVal);
//        System.out.println(listOfPutValues);
        model.addAttribute("putVal", listOfPutValues);
        model.addAttribute("callVal", listOfCallValues);

        List<Double> listOfOiPutValues = new LinkedList<>(OiPutVal.values());
        List<Double> listOfOiCallValues = new LinkedList<>(OiCallVal.values());
        model.addAttribute("OiPutVal", listOfOiPutValues);
        model.addAttribute("OiCallVal", listOfOiCallValues);
        model.addAttribute("spotPrice", nse.getRecords().getUnderlyingValue());

        List<String> operators = new ArrayList<>();
        operators.add("NIFTY");
        operators.add("BANKNIFTY");
        model.addAttribute("operators", operators);

//      getting pcr value

        Double putValue = processor.processBankNiftyData(bankNifty.getLiveBankNiftyData(FeignBuilder.builder()), 1000, 100).getPutTotalOI();
        Double callValue = processor.processBankNiftyData(bankNifty.getLiveBankNiftyData(FeignBuilder.builder()), 1000, 100).getCallTotalOI();

        float pcr = Float.parseFloat(String.format("%.2f", (putValue / callValue)));

//        String.format("%.2f", pcr);
        System.out.println(pcr);
        model.addAttribute("pcr", pcr);

        return "BankNiftyPage";

    }

    @GetMapping("/NiftyPage")
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
        List<Double> strikePrice = new LinkedList<>(keySet);
        System.out.println(putVal.keySet());
        model.addAttribute("strikePrice", strikePrice);

        // Creating an ArrayList of values
        List<Double> listOfPutValues = new LinkedList<>(putVal.values());
        List<Double> listOfCallValues = new LinkedList<>(callVal.values());
//        System.out.println(putVal);
//        System.out.println(listOfPutValues);
        model.addAttribute("putVal", listOfPutValues);
        model.addAttribute("callVal", listOfCallValues);

        List<Double> listOfOiPutValues = new LinkedList<>(OiPutVal.values());
        List<Double> listOfOiCallValues = new LinkedList<>(OiCallVal.values());
        model.addAttribute("OiPutVal", listOfOiPutValues);
        model.addAttribute("OiCallVal", listOfOiCallValues);
        model.addAttribute("spotPrice", nse.getRecords().getUnderlyingValue());

        List<String> operators = new ArrayList<>();
        operators.add("NIFTY");
        operators.add("BANKNIFTY");
        model.addAttribute("operators", operators);

//      getting pcr value

        Double putValue = processor.processNiftyData(nifty.getLiveNiftyData(FeignBuilder.builder()), 500, 50).getPutTotalOI();
        Double callValue = processor.processNiftyData(nifty.getLiveNiftyData(FeignBuilder.builder()), 500, 50).getCallTotalOI();

        float pcr = Float.parseFloat(String.format("%.2f", (putValue / callValue)));

//        String.format("%.2f", pcr);
        System.out.println(pcr);
        model.addAttribute("pcr", pcr);
        return "barChart";
    }

    @GetMapping("/FinNiftyPage")
    public String getFinNiftyPrice(Model model) {
        List<Data> records;

        NSE nse = finNifty.getLiveFinNiftyData(FeignBuilder.builder());

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
        List<Double> strikePrice = new LinkedList<>(keySet);
        System.out.println(putVal.keySet());
        model.addAttribute("strikePrice", strikePrice);

        // Creating an ArrayList of values
        List<Double> listOfPutValues = new LinkedList<>(putVal.values());
        List<Double> listOfCallValues = new LinkedList<>(callVal.values());
//        System.out.println(putVal);
//        System.out.println(listOfPutValues);
        model.addAttribute("putVal", listOfPutValues);
        model.addAttribute("callVal", listOfCallValues);

        List<Double> listOfOiPutValues = new LinkedList<>(OiPutVal.values());
        List<Double> listOfOiCallValues = new LinkedList<>(OiCallVal.values());
        model.addAttribute("OiPutVal", listOfOiPutValues);
        model.addAttribute("OiCallVal", listOfOiCallValues);
        model.addAttribute("spotPrice", nse.getRecords().getUnderlyingValue());

        List<String> operators = new ArrayList<>();
        operators.add("NIFTY");
        operators.add("BANKNIFTY");
        model.addAttribute("operators", operators);

//      getting pcr value

        Double putValue = processor.processNiftyData(finNifty.getLiveFinNiftyData(FeignBuilder.builder()), 500, 50).getPutTotalOI();
        Double callValue = processor.processNiftyData(finNifty.getLiveFinNiftyData(FeignBuilder.builder()), 500, 50).getCallTotalOI();

        float pcr = Float.parseFloat(String.format("%.2f", (putValue / callValue)));

//        String.format("%.2f", pcr);
        System.out.println(pcr);
        model.addAttribute("pcr", pcr);
        return "FinNiftyPage";

    }

    @GetMapping("/MidCpNiftyPage")
    public String getMidCpNiftyPageStrikePrice(Model model) {
        List<Data> records;

        NSE nse = nifty.getLiveMIDCPNiftyData(FeignBuilder.builder());

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
        List<Double> strikePrice = new LinkedList<>(keySet);
        System.out.println(putVal.keySet());
        model.addAttribute("strikePrice", strikePrice);

        // Creating an ArrayList of values
        List<Double> listOfPutValues = new LinkedList<>(putVal.values());
        List<Double> listOfCallValues = new LinkedList<>(callVal.values());

        model.addAttribute("putVal", listOfPutValues);
        model.addAttribute("callVal", listOfCallValues);

        List<Double> listOfOiPutValues = new LinkedList<>(OiPutVal.values());
        List<Double> listOfOiCallValues = new LinkedList<>(OiCallVal.values());
        model.addAttribute("OiPutVal", listOfOiPutValues);
        model.addAttribute("OiCallVal", listOfOiCallValues);
        model.addAttribute("spotPrice", nse.getRecords().getUnderlyingValue());

        List<String> operators = new ArrayList<>();
        operators.add("NIFTY");
        operators.add("BANKNIFTY");
        model.addAttribute("operators", operators);

//      getting pcr value

        Double putValue = processor.processNiftyData(nifty.getLiveNiftyData(FeignBuilder.builder()), 500, 50).getPutTotalOI();
        Double callValue = processor.processNiftyData(nifty.getLiveNiftyData(FeignBuilder.builder()), 500, 50).getCallTotalOI();

        float pcr = Float.parseFloat(String.format("%.2f", (putValue / callValue)));

//        String.format("%.2f", pcr);
        System.out.println(pcr);
        model.addAttribute("pcr", pcr);
        return "MidCpNiftyPage";
    }


    @GetMapping("/stocks")
    public String showAnalysisStocks1(Model model) {
        NSE nse = stocks.getLiveStocksData(FeignBuilder.builder());
        model.addAttribute("expiredate", nse.getRecords().getExpiryDates());
        return "index";
    }

}
