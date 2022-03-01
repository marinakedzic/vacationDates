package com.marina.vacationDates.controller;

import com.marina.vacationDates.model.TotalVacationDays;
import jdk.management.resource.internal.TotalResourceContext;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/vacationApp")
public class VacationDatesAppController {
    
    @RequestMapping(value = {"/","/index"}, method = RequestMethod.GET)
    public ModelAndView showUsers(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TotalVacationDays[]> response =
            restTemplate.getForEntity(
                    "http://localhost:8081/totalDays/employees",
                    TotalVacationDays[].class);
        TotalVacationDays[] employees = response.getBody();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("employees", employees);
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
