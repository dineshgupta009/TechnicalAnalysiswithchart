package com.example.TechnicalAnalysis.controller.view;

import com.example.TechnicalAnalysis.entity.NSE;
import com.example.TechnicalAnalysis.entity.marketStatus.Status;
import com.example.TechnicalAnalysis.feign.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MarketStatus {

    @Autowired
    FeignClientMarketStatus marketStatus;

    @GetMapping("symbol1")
    public String showAnalysis(Model model) {
        Status statusData = marketStatus.getMarketStatusData(FeignBuilder.builder());
        model.addAttribute("status", statusData.marketState);
        return "index";
    }

}
