package com.example.TechnicalAnalysis.processor;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.TechnicalAnalysis.entity.Data;
import com.example.TechnicalAnalysis.entity.FilteredResponse;
import com.example.TechnicalAnalysis.entity.NSE;

@Component
public class NSEDataProcessor {

    long niftyCount = 0;
    List<Data> niftyPastRecords = new ArrayList<Data>();

    long bankNiftyCount = 0;
    double crore = 10000000;
    List<Data> bankNiftyPastRecords = new ArrayList<Data>();

    public FilteredResponse processNiftyData(NSE nse, double limit, double round) {

        HashMap<Double, Double> putSortedLevels = new HashMap<>();
        HashMap<Double, Double> callSortedLevels = new HashMap<>();

        HashMap<Double, Double> putLevels = new HashMap<>();
        HashMap<Double, Double> callLevels = new HashMap<>();

        List<Data> records = new ArrayList<Data>();

        double pastUnderlyingPrice = 0;

        double currentStrike = Math.round((nse.getRecords().getUnderlyingValue() / round)) * round;

        records = Arrays.asList(nse.getFiltered().getData()).stream()
                .filter(data -> (data.getStrikePrice() >= (currentStrike - limit) && data.getStrikePrice() <= (currentStrike + limit)))
                .collect(Collectors.toList());





        for (Data data : records) {
            putLevels.put(data.getStrikePrice(), (data.getPe().getChangeinOpenInterest() * data.getPe().getTotalTradedVolume()) / crore);
            callLevels.put(data.getStrikePrice(), (data.getCe().getChangeinOpenInterest() * data.getCe().getTotalTradedVolume()) / crore);
        }


        putLevels.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .forEachOrdered(x -> putSortedLevels.put(x.getKey(), x.getValue()));

        callLevels.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .forEachOrdered(x -> callSortedLevels.put(x.getKey(), x.getValue()));

        if (niftyCount == 0) {
            niftyPastRecords = records;
        }

        FilteredResponse response = new FilteredResponse();
        response.setUnderlyingPrice(nse.getRecords().getUnderlyingValue());
        response.setCurrentStrike(currentStrike);
        response.setPastRecords(niftyPastRecords);
        response.setPastUnderlyingPrice(pastUnderlyingPrice);
        response.setRecords(records);
        response.setCallLevels(callSortedLevels);
        response.setPutLevels(putSortedLevels);
        niftyPastRecords = records;
        pastUnderlyingPrice = nse.getRecords().getUnderlyingValue();
        response.setPutTotalOI(nse.getFiltered().getPe().getTotOI());
        response.setCallTotalOI(nse.getFiltered().getCe().getTotOI());
        response.setPutTotalVolume(nse.getFiltered().getPe().getTotVol());
        response.setCallTotalVolume(nse.getFiltered().getCe().getTotVol());
        niftyCount++;

        return response;
    }

    public FilteredResponse processBankNiftyData(NSE nse, double limit, double round) {

        HashMap<Double, Double> putSortedLevels = new HashMap<>();
        HashMap<Double, Double> callSortedLevels = new HashMap<>();

        HashMap<Double, Double> putLevels = new HashMap<>();
        HashMap<Double, Double> callLevels = new HashMap<>();

        List<Data> records = new ArrayList<Data>();

        double pastUnderlyingPrice = 0;

        double currentStrike = Math.round((nse.getRecords().getUnderlyingValue() / round)) * round;

        records = Arrays.asList(nse.getFiltered().getData()).stream()
                .filter(data -> (data.getStrikePrice() >= (currentStrike - limit) && data.getStrikePrice() <= (currentStrike + limit)))
                .collect(Collectors.toList());


        for (Data data : records) {
            putLevels.put(data.getStrikePrice(), (data.getPe().getChangeinOpenInterest() * data.getPe().getTotalTradedVolume()) / crore);
            callLevels.put(data.getStrikePrice(), (data.getCe().getChangeinOpenInterest() * data.getCe().getTotalTradedVolume()) / crore);
        }

        System.out.println(putLevels);
        System.out.println(callLevels);
        putLevels.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .forEachOrdered(x -> putSortedLevels.put(x.getKey(), x.getValue()));

        callLevels.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .forEachOrdered(x -> callSortedLevels.put(x.getKey(), x.getValue()));

        if (bankNiftyCount == 0) {
            bankNiftyPastRecords = records;
        }

        FilteredResponse response = new FilteredResponse();
        response.setUnderlyingPrice(nse.getRecords().getUnderlyingValue());
        response.setCurrentStrike(currentStrike);
        response.setPastRecords(bankNiftyPastRecords);
        response.setPastUnderlyingPrice(pastUnderlyingPrice);
        response.setRecords(records);
        response.setCallLevels(callSortedLevels);
        response.setPutLevels(putSortedLevels);
        response.setPutTotalOI(nse.getFiltered().getPe().getTotOI());
        response.setCallTotalOI(nse.getFiltered().getCe().getTotOI());
        response.setPutTotalVolume(nse.getFiltered().getPe().getTotVol());
        response.setCallTotalVolume(nse.getFiltered().getCe().getTotVol());
        bankNiftyPastRecords = records;
        pastUnderlyingPrice = nse.getRecords().getUnderlyingValue();
        bankNiftyCount++;

        return response;
    }


    public List<Double> getPutStrikePrice(NSE nse, double limit, double round) {

        HashMap<Double, Double> putSortedLevels = new HashMap<>();
        HashMap<Double, Double> callSortedLevels = new HashMap<>();

        HashMap<Double, Double> putLevels = new HashMap<>();
        HashMap<Double, Double> callLevels = new HashMap<>();

        List<Data> records = new ArrayList<Data>();

        double pastUnderlyingPrice = 0;

        double currentStrike = Math.round((nse.getRecords().getUnderlyingValue() / round)) * round;

        records = Arrays.asList(nse.getFiltered().getData()).stream()
                .filter(data -> (data.getStrikePrice() >= (currentStrike - limit) && data.getStrikePrice() <= (currentStrike + limit)))
                .collect(Collectors.toList());


        for (Data data : records) {
            putLevels.put(data.getStrikePrice(), (data.getPe().getChangeinOpenInterest()));
            callLevels.put(data.getStrikePrice(), (data.getCe().getChangeinOpenInterest()));

        }

        Set<Double> keySet = putLevels.keySet();
        ArrayList<Double> strikePrice
                = new ArrayList<Double>(keySet);

        System.out.println(putLevels.keySet());

        // Getting Collection of values from HashMap
        Collection<Double> values = putLevels.values();

        // Creating an ArrayList of values
        ArrayList<Double> listOfValues
                = new ArrayList<>(values);
        return strikePrice;
    }

    public List<Double> getPutVol(NSE nse, double limit, double round) {

        HashMap<Double, Double> putSortedLevels = new HashMap<>();
        HashMap<Double, Double> callSortedLevels = new HashMap<>();

        HashMap<Double, Double> putLevels = new HashMap<>();
        HashMap<Double, Double> callLevels = new HashMap<>();

        List<Data> records = new ArrayList<Data>();

        double pastUnderlyingPrice = 0;

        double currentStrike = Math.round((nse.getRecords().getUnderlyingValue() / round)) * round;
//          Just get few strike price
        records = Arrays.asList(nse.getFiltered().getData()).stream()
                .filter(data -> (data.getStrikePrice() >= (currentStrike - limit) && data.getStrikePrice() <= (currentStrike + limit)))
                .collect(Collectors.toList());

        for (Data data : records) {
            putLevels.put(data.getStrikePrice(), (data.getPe().getChangeinOpenInterest()));
            callLevels.put(data.getStrikePrice(), (data.getCe().getChangeinOpenInterest()));

        }

        Set<Double> keySet = putLevels.keySet();
        ArrayList<Double> strikePrice
                = new ArrayList<Double>(keySet);
        // Getting Collection of values from HashMap
        Collection<Double> values = putLevels.values();

        // Creating an ArrayList of values
        ArrayList<Double> Volume
                = new ArrayList<>(values);
        return Volume;
    }

    public List<Double> getCallVol(NSE nse, double limit, double round) {

        LinkedHashMap<Double, Double> putSortedLevels = new LinkedHashMap<>();
        LinkedHashMap<Double, Double> callSortedLevels = new LinkedHashMap<>();

        LinkedHashMap<Double, Double> putLevels = new LinkedHashMap<>();
        LinkedHashMap<Double, Double> callLevels = new LinkedHashMap<>();

        List<Data> records = new ArrayList<Data>();

        double pastUnderlyingPrice = 0;

        double currentStrike = Math.round((nse.getRecords().getUnderlyingValue() / round)) * round;

        records = Arrays.asList(nse.getFiltered().getData()).stream()
                .filter(data -> (data.getStrikePrice() >= (currentStrike - limit) && data.getStrikePrice() <= (currentStrike + limit)))
                .collect(Collectors.toList());

        for (Data data : records) {
            putLevels.put(data.getStrikePrice(), (data.getPe().getChangeinOpenInterest()));
            callLevels.put(data.getStrikePrice(), (data.getCe().getChangeinOpenInterest()));
        }

        Set<Double> keySet = callLevels.keySet();
        ArrayList<Double> strikePrice
                = new ArrayList<Double>(keySet);

        // Getting Collection of values from HashMap
        Collection<Double> values = callLevels.values();

        // Creating an ArrayList of values
        ArrayList<Double> Volume
                = new ArrayList<>(values);
        System.out.println(Volume);
        return Volume;
    }


}
