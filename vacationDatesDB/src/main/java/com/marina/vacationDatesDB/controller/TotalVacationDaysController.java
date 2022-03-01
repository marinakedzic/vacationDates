package com.marina.vacationDatesDB.controller;

import com.marina.vacationDatesDB.service.TotalVacationDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/totalDays")
public class TotalVacationDaysController {

    @Autowired
    TotalVacationDaysService totalVacationDaysService;

    @RequestMapping(value = {"/employees"}, method = RequestMethod.GET)
    public List<String> findAllEmployees(){
        List<String> listusers = totalVacationDaysService.findAllEmployees();
        return listusers;
    }

}