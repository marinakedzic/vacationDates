package com.marina.vacationDatesDB.controller;

import com.marina.vacationDatesDB.model.UsedVacationDays;
import com.marina.vacationDatesDB.service.UsedVacationDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usedDays")
public class UsedVacationDaysController {

    @Autowired
    UsedVacationDaysService usedVacationDaysService;

    @RequestMapping(value = {"/{employee}"}, method = RequestMethod.GET)
    public List<UsedVacationDays> findTotalVacationDaysByYear(@PathVariable("employee") String employee){
        List<UsedVacationDays> listusers = usedVacationDaysService.findUsedVacationDaysByEmployee(employee);
        return listusers;
    }

}