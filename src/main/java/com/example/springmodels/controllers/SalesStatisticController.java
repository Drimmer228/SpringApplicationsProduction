package com.example.springmodels.controllers;

import com.example.springmodels.ApiInterface;
import com.example.springmodels.models.SalesStatisticsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/statistic")
public class SalesStatisticController {
    private final ApiInterface apiInterface;

    @Autowired
    public SalesStatisticController(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }
    @GetMapping("/{id}")
    private String getPageStatistic(@PathVariable long id, Model model){
        model.addAttribute("appl", apiInterface.getApplicationAtId(id));
        return "application/statistic_page";
    }

    @GetMapping("/salesData/{id}")
    public ResponseEntity<List<SalesStatisticsModel>> downloadImage(@PathVariable Long id) {
        List<SalesStatisticsModel> salesStatisticsModelList = apiInterface.findAllByApplicationId(id);
        if (salesStatisticsModelList != null) {
            HttpHeaders headers = new HttpHeaders();
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(salesStatisticsModelList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
