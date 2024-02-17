package com.example.TechnicalAnalysis.controller.view;


import com.example.TechnicalAnalysis.entity.Data;
import com.example.TechnicalAnalysis.entity.FilteredResponse;
import com.example.TechnicalAnalysis.entity.marketStatus.MarketState;
import com.example.TechnicalAnalysis.entity.marketStatus.Status;
import com.example.TechnicalAnalysis.feign.*;
import com.example.TechnicalAnalysis.processor.NSEDataProcessor;
import com.example.TechnicalAnalysis.processor.StocksDataProcessor;
import com.sun.source.tree.Tree;
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

    @Autowired
    StocksDataProcessor stocksDataProcessor;

    @Autowired
    FeignClientMarketStatus marketStatus;

    @GetMapping("symbol")
    public String showAnalysis(Model model) {
        NSE nse = nifty.getLiveNiftyData(FeignBuilder.builder());
        model.addAttribute("expiredate", nse.getRecords().getExpiryDates());

        Status statusData = marketStatus.getMarketStatusData(FeignBuilder.builder());

        System.out.println();
        model.addAttribute("status", statusData.getIndicativenifty50().getChange());
        model.addAttribute("status1", statusData.getIndicativenifty50());
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

        boolean data1 = Arrays.asList(nse.getFiltered().getData()).stream()
                .filter(data -> (data.getStrikePrice() == (currentStrike)))
                .allMatch(data -> data.getPe().getImpliedVolatility() > data.getCe().getImpliedVolatility());

        model.addAttribute("upDown", data1);

        for (Data data : records) {
            putVal.put(data.getStrikePrice(), (data.getPe().getChangeinOpenInterest()));
            callVal.put(data.getStrikePrice(), (data.getCe().getChangeinOpenInterest()));
            OiPutVal.put(data.getStrikePrice(), (data.getPe().getOpenInterest()));
            OiCallVal.put(data.getStrikePrice(), (data.getCe().getOpenInterest()));
        }
//        getting both key and value for highest 3 volume

        Map<Double, Double> HigPut = OiPutVal.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.println("Put Val " + HigPut);
        TreeSet<Double> higPutKey = new TreeSet<>(HigPut.keySet());
        System.out.println(higPutKey);
        model.addAttribute("SupportPut" ,higPutKey);



        Map<Double, Double> HigCall = OiCallVal.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        System.out.println("Call Val " + HigCall);
        TreeSet<Double> higCallKey = new TreeSet<>(HigCall.keySet());
        System.out.println(higPutKey);
        model.addAttribute("SupportCall" ,higCallKey);

//        Getting list of Strike price
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


    @GetMapping("/CoalIndiaPage")
    public String showAnalysisCoalIndia(Model model) {
        NSE nse = stocks.getLiveCoalIndiaData(FeignBuilder.builder());
        model.addAttribute("expiredate", nse.getRecords().getExpiryDates());
        List<Data> records;


        Map<Double, Double> putVal = new LinkedHashMap<>();
        Map<Double, Double> callVal = new LinkedHashMap<>();

        Map<Double, Double> OiPutVal = new LinkedHashMap<>();
        Map<Double, Double> OiCallVal = new LinkedHashMap<>();

        double currentStrike = Math.round((nse.getRecords().getUnderlyingValue() / 2.5)) * 2.5;

        records = Arrays.asList(nse.getFiltered().getData()).stream()
                .filter(data -> (data.getStrikePrice() >= (currentStrike - 30) && data.getStrikePrice() <= (currentStrike + 30)))
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

        Double putValue = stocksDataProcessor.processStocksData(stocks.getLiveCoalIndiaData(FeignBuilder.builder()), 50, 2.5).getPutTotalOI();
        Double callValue = stocksDataProcessor.processStocksData(stocks.getLiveCoalIndiaData(FeignBuilder.builder()), 50, 2.5).getCallTotalOI();

        float pcr = Float.parseFloat(String.format("%.2f", (putValue / callValue)));

        String.format("%.2f", pcr);
        System.out.println(pcr);
        model.addAttribute("pcr", pcr);
        return "CoalIndiaPage";
    }

    @GetMapping("/AartiIndPage")
    public String showAnalysisAartiInd(Model model) {
        NSE nse = stocks.getLiveAartiIndData(FeignBuilder.builder());
        model.addAttribute("expiredate", nse.getRecords().getExpiryDates());
        List<Data> records;


        Map<Double, Double> putVal = new LinkedHashMap<>();
        Map<Double, Double> callVal = new LinkedHashMap<>();

        Map<Double, Double> OiPutVal = new LinkedHashMap<>();
        Map<Double, Double> OiCallVal = new LinkedHashMap<>();


        double currentStrike = Math.round((nse.getRecords().getUnderlyingValue() / 5)) * 5;

        records = Arrays.asList(nse.getFiltered().getData()).stream()
                .filter(data -> (data.getStrikePrice() >= (currentStrike - 60) && data.getStrikePrice() <= (currentStrike + 60)))
                .collect(Collectors.toList());
        for (Data data : records) {

            putVal.put(data.getStrikePrice(), (data.getPe().getChangeinOpenInterest()));
            callVal.put(data.getStrikePrice(), (data.getCe().getChangeinOpenInterest()));
            OiPutVal.put(data.getStrikePrice(), (data.getPe().getOpenInterest()));
            OiCallVal.put(data.getStrikePrice(), (data.getCe().getOpenInterest()));

        }

        Set<Double> keySet = putVal.keySet();
        List<Double> strikePrice = new LinkedList<>(keySet);
        System.out.println("Strike price " + putVal.keySet());
        model.addAttribute("strikePrice", strikePrice);


        // Creating an ArrayList of values
        List<Double> listOfPutValues = new LinkedList<>(putVal.values());
        List<Double> listOfCallValues = new LinkedList<>(callVal.values());

        model.addAttribute("putVal", listOfPutValues);
        model.addAttribute("callVal", listOfCallValues);

        List<Double> listOfOiPutValues = new LinkedList<>(OiPutVal.values());
        System.out.println(listOfOiPutValues);
        List<Double> listOfOiCallValues = new LinkedList<>(OiCallVal.values());
        model.addAttribute("OiPutVal", listOfOiPutValues);

        model.addAttribute("OiCallVal", listOfOiCallValues);

        //        getting both key and value for highest 3 volume

        Map<Double, Double> HigPut = OiPutVal.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.println("Put Val " + HigPut);
        Map<Double, Double> HigCall = OiCallVal.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.println("Call Val " + HigCall);


        model.addAttribute("spotPrice", nse.getRecords().getUnderlyingValue());

//      getting pcr value

        Double putValue = stocksDataProcessor.processStocksData(stocks.getLiveAartiIndData(FeignBuilder.builder()), 50, 5).getPutTotalOI();
        Double callValue = stocksDataProcessor.processStocksData(stocks.getLiveAartiIndData(FeignBuilder.builder()), 50, 5).getCallTotalOI();

        float pcr = Float.parseFloat(String.format("%.2f", (putValue / callValue)));


        String.format("%.2f", pcr);
        System.out.println(pcr);
        model.addAttribute("pcr", pcr);
        return "AartiIndPage";
    }

    @GetMapping("/GujGasPage")
    public String showAnalysisGujGas(Model model) {
        NSE nse = stocks.getLiveAartiIndData(FeignBuilder.builder());
        model.addAttribute("expiredate", nse.getRecords().getExpiryDates());
        List<Data> records;


        Map<Double, Double> putVal = new LinkedHashMap<>();
        Map<Double, Double> callVal = new LinkedHashMap<>();

        Map<Double, Double> OiPutVal = new LinkedHashMap<>();
        Map<Double, Double> OiCallVal = new LinkedHashMap<>();


        double currentStrike = Math.round((nse.getRecords().getUnderlyingValue() / 5)) * 5;

        records = Arrays.asList(nse.getFiltered().getData()).stream()
                .filter(data -> (data.getStrikePrice() >= (currentStrike - 60) && data.getStrikePrice() <= (currentStrike + 60)))
                .collect(Collectors.toList());
        for (Data data : records) {

            putVal.put(data.getStrikePrice(), (data.getPe().getChangeinOpenInterest()));
            callVal.put(data.getStrikePrice(), (data.getCe().getChangeinOpenInterest()));
            OiPutVal.put(data.getStrikePrice(), (data.getPe().getOpenInterest()));
            OiCallVal.put(data.getStrikePrice(), (data.getCe().getOpenInterest()));

        }

        Set<Double> keySet = putVal.keySet();
        List<Double> strikePrice = new LinkedList<>(keySet);
        System.out.println("Strike price " + putVal.keySet());
        model.addAttribute("strikePrice", strikePrice);


        // Creating an ArrayList of values
        List<Double> listOfPutValues = new LinkedList<>(putVal.values());
        List<Double> listOfCallValues = new LinkedList<>(callVal.values());

        model.addAttribute("putVal", listOfPutValues);
        model.addAttribute("callVal", listOfCallValues);

        List<Double> listOfOiPutValues = new LinkedList<>(OiPutVal.values());
        System.out.println(listOfOiPutValues);
        List<Double> listOfOiCallValues = new LinkedList<>(OiCallVal.values());
        model.addAttribute("OiPutVal", listOfOiPutValues);

        model.addAttribute("OiCallVal", listOfOiCallValues);

        //        getting both key and value for highest 3 volume

        Map<Double, Double> HigPut = OiPutVal.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.println("Put Val " + HigPut);
        Map<Double, Double> HigCall = OiCallVal.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.println("Call Val " + HigCall);


        model.addAttribute("spotPrice", nse.getRecords().getUnderlyingValue());

//      getting pcr value

        Double putValue = stocksDataProcessor.processStocksData(stocks.getLiveAartiIndData(FeignBuilder.builder()), 50, 5).getPutTotalOI();
        Double callValue = stocksDataProcessor.processStocksData(stocks.getLiveAartiIndData(FeignBuilder.builder()), 50, 5).getCallTotalOI();

        float pcr = Float.parseFloat(String.format("%.2f", (putValue / callValue)));


        String.format("%.2f", pcr);
        System.out.println(pcr);
        model.addAttribute("pcr", pcr);
        return "GujGasPage";
    }
}
