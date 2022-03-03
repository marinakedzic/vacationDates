package com.marina.vacationDatesDB.controller;

import com.marina.vacationDatesDB.model.TotalVacationDays;
import com.marina.vacationDatesDB.service.TotalVacationDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;

@RestController
@RequestMapping("/totalDays")
public class TotalVacationDaysController {

    @Autowired
    TotalVacationDaysService totalVacationDaysService;
    //find all employees from Database
    @RequestMapping(value = {"/employees"}, method = RequestMethod.GET)
    public List<TotalVacationDays> findAllEmployees(){
        List<TotalVacationDays> listusers = totalVacationDaysService.findAllEmployees();
        return listusers;
    }

}