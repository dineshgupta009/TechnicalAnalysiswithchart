package com.example.TechnicalAnalysis.processor.test;

import com.example.TechnicalAnalysis.entity.Data;
import com.example.TechnicalAnalysis.entity.FilteredResponse;
import com.example.TechnicalAnalysis.entity.NSE;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class NseDataChart {

    public List<Double> getCallVol(NSE nse, double limit, double round) {

        LinkedHashMap<Double, Double> putLevels = new LinkedHashMap<>();
        LinkedHashMap<Double, Double> callLevels = new LinkedHashMap<>();
        List<Data> records = new LinkedList<>();
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
        List<Double> strikePrice= new LinkedList<Double>(keySet);

        // Getting Collection of values from HashMap
//        Collection<Double> values = callLevels.values();

        // Creating an ArrayList of values
        LinkedList<Double> Volume= new LinkedList<>(callLevels.values());
        System.out.println(Volume);
        return Volume;
    }

}
