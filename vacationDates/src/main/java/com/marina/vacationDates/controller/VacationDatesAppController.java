package com.marina.vacationDates.controller;

import com.marina.vacationDates.model.TotalVacationDays;
import com.marina.vacationDates.model.UsedVacationDays;
import jdk.management.resource.internal.TotalResourceContext;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/vacationApp")
public class VacationDatesAppController {

    @RequestMapping(value = {"/allUsers"}, method = RequestMethod.GET)
    public ModelAndView showUsers(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TotalVacationDays[]> response =
            restTemplate.getForEntity(
                    "http://localhost:8081/totalDays/employees",
                    TotalVacationDays[].class);
        TotalVacationDays[] employees = response.getBody();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("employees", employees);
        modelAndView.setViewName("allUsers");
        return modelAndView;
    }
    @RequestMapping(value = {"/","/index"}, method = RequestMethod.GET)
    public ModelAndView total(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response =
                restTemplate.getForEntity(
                        "http://localhost:8081/usedDays/totalUnusedDays",
                        String.class);
        String total = response.getBody();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("total", total);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/employee/vacations/{employee}")
    public ModelAndView employeeVacations (@PathVariable("employee") String employee){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UsedVacationDays[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8081/usedDays/" + employee,
                        UsedVacationDays[].class);
        UsedVacationDays[] days = response.getBody();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("name", employee);
        modelAndView.addObject("days", days);
        modelAndView.setViewName("employeeVacations");
        return modelAndView;
    }
}
