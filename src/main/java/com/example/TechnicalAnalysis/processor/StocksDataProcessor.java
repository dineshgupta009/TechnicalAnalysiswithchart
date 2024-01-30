package com.example.TechnicalAnalysis.processor;

import com.example.TechnicalAnalysis.entity.Data;
import com.example.TechnicalAnalysis.entity.FilteredResponse;
import com.example.TechnicalAnalysis.entity.NSE;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class StocksDataProcessor {

    long niftyCount = 0;
    List<Data> niftyPastRecords = new ArrayList<Data>();

    long bankNiftyCount = 0;
    double crore = 10000000;
    List<Data> bankNiftyPastRecords = new ArrayList<Data>();

    public FilteredResponse processStocksData(NSE nse, double limit, double round) {

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


}
