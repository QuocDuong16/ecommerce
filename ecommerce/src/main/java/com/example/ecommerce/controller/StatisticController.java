package com.example.ecommerce.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.ecommerce.service.StatisticService;

@Controller
public class StatisticController {
	
	@Autowired
    private StatisticService statisticService;

    @GetMapping("/statistic")
    public String viewStatistic(Model model) {
        // Mã xử lý khi trang được load lần đầu tiên
        statisticService.prepareInitialData(model);
        return "admin/statistic";
    }

    @PostMapping("/statistic")
    @ResponseBody
    public Map<String, List<?>> handleStatisticFilter(@RequestParam String startDate,
                                        @RequestParam String endDate,
                                        @RequestParam String filterType) {
    	Map<String, List<?>> dataMap = new HashMap<>();
        if ("customer".equals(filterType)) {
        	dataMap = statisticService.handleStatisticFilter(startDate, endDate, "customer");
        } else if ("product".equals(filterType)) {
        	dataMap = statisticService.handleStatisticFilter(startDate, endDate, "product");
        } else if ("seller".equals(filterType)) {
        	dataMap = statisticService.handleStatisticFilter(startDate, endDate, "seller");
        }
        return dataMap;
    }
}
