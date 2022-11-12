package com.marina.vacationDatesDB.controller;

import com.marina.vacationDatesDB.model.TotalVacationDays;
import com.marina.vacationDatesDB.model.UsedVacationDays;
import com.marina.vacationDatesDB.service.TotalVacationDaysService;
import com.marina.vacationDatesDB.service.UsedVacationDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeDetailsController {
    @Autowired
    TotalVacationDaysService totalVacationDaysService;
    @Autowired
    UsedVacationDaysService usedVacationDaysService;

    //Total number of vacation days for each year

    @RequestMapping(value = {"/totalVacationDaysByUser/{employee}"}, method = RequestMethod.GET)
    public Map<String, String> totalVacationDaysByUser(@PathVariable("employee") String employee) throws ParseException {
        return totalVacationDaysService.findTotalVacationDaysByEmployee(employee);
    }

    //Total number of unused vacation days for each year

    @RequestMapping(value = {"/totalUnusedDaysByUser/{employee}"}, method = RequestMethod.GET)
    public Map<String, String> totalUnusedDaysByUser(@PathVariable("employee") String employee) throws ParseException {
        List<String> ListOfYears = Arrays.asList("2019", "2020", "2021");
        Map<String, String> all = new HashMap<>();
        for (String year : ListOfYears){
            Map<String, String> vacationDays = totalVacationDaysService.findTotalVacationDaysByEmployee(employee);
            Map<String, String> usedDays = usedVacationDaysService.findTotalUsedDaysInYearForOne(employee,year);
            for (Map.Entry<String, String> vacation : vacationDays.entrySet()) {
                for (Map.Entry<String, String> used : usedDays.entrySet()) {
                    if (vacation.getKey().equals(used.getKey())) {
                        int to = Integer.parseInt(vacation.getValue()) - Integer.parseInt(used.getValue());
                        all.put(year, String.valueOf(to));
                    }
                }
            }

        }
        return all;
    }

    //Number of vacation days used for each month

    @RequestMapping(value = {"/byMonth/{employee}"}, method = RequestMethod.GET)
    public Map<String, String> byMonth(@PathVariable("employee") String employee) throws ParseException {
        List <String> ListOfYears = Arrays.asList("2019", "2020", "2021");
        Map<String, String> allMonths = new HashMap<>();
        for (String year : ListOfYears){
            Map<String, String> usedDays = usedVacationDaysService.numberOfVacationDatesByMonthForOne(employee,year);
            for (Map.Entry<String, String> used : usedDays.entrySet()) {
                if (allMonths.containsKey(used.getKey())) {
                    int to = Integer.parseInt(used.getValue()) + Integer.parseInt(allMonths.get(used.getKey()));
                    allMonths.put(used.getKey(), String.valueOf(to));
                }
                else{
                    allMonths.put(used.getKey(), used.getValue());
                }
            }
        }
        return allMonths;
    }

    //Dates when the user took vacation days and for how long

    @RequestMapping(value = {"when/{employee}"}, method = RequestMethod.GET)
    public List<UsedVacationDays> when(@PathVariable("employee") String employee){
        return usedVacationDaysService.findUsedVacationDaysByEmployee(employee);
    }

    @RequestMapping(value = {"howLong/{employee}"}, method = RequestMethod.GET)
    public List<Integer> howLong(@PathVariable("employee") String employee) throws ParseException{
        return usedVacationDaysService.findUsedVacationDaysByEmployeeAndHowLong(employee);
    }

    //Clicking on a user opens up the user vacation details screen

    //Find all employees from Database
    @RequestMapping(value = {"/employees"}, method = RequestMethod.GET)
    public List<TotalVacationDays> findAllEmployees(){
        return totalVacationDaysService.findAllEmployees();
    }
}
