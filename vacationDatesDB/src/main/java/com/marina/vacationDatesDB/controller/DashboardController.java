package com.marina.vacationDatesDB.controller;

import com.marina.vacationDatesDB.service.TotalVacationDaysService;
import com.marina.vacationDatesDB.service.UsedVacationDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    TotalVacationDaysService totalVacationDaysService;
    @Autowired
    UsedVacationDaysService usedVacationDaysService;


    //Total number of unused vacation days among all users

    @RequestMapping(value = {"/totalUnusedDays"}, method = RequestMethod.GET)
    public Integer totalUnusedDays(String year) throws ParseException {
        Map<String, String> vacationDays = totalVacationDaysService.findTotalVacationDaysByYearForAll(2021);
        Map<String, String> usedDays = usedVacationDaysService.numberOfVacationDatesForAllOneYear(String.valueOf(2021));
        List<Integer> finalDays = new ArrayList<>();
        for (Map.Entry<String, String> vacation : vacationDays.entrySet()) {
            for (Map.Entry<String, String> used : usedDays.entrySet()) {
                if (vacation.getKey().equals(used.getKey())) {
                    int to = Integer.parseInt(vacation.getValue()) - Integer.parseInt(used.getValue());
                    finalDays.add(to);
                }
            }
        }
        return finalDays.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    //Percentage of users that used all vacation days for 2021

    @RequestMapping(value = {"/percentage"}, method = RequestMethod.GET)
    public Integer percentage() throws ParseException {
        int percentage;
        Map<String, String> vacationDays = totalVacationDaysService.findTotalVacationDaysByYearForAll(2021);
        Map<String, String> usedDays = usedVacationDaysService.numberOfVacationDatesForAllOneYear(String.valueOf(2021));
        Map<String, String> finalDays = new HashMap<>();
        for (Map.Entry<String, String> vacation : vacationDays.entrySet()) {
            for (Map.Entry<String, String> used : usedDays.entrySet()) {
                if (vacation.getKey().equals(used.getKey())) {
                    int to = Integer.parseInt(vacation.getValue()) - Integer.parseInt(used.getValue());
                    finalDays.put(vacation.getKey(), String.valueOf(to));
                }
            }
        }
        int part = 0;
        for (Map.Entry<String, String> finalM : finalDays.entrySet()) {
            if (Integer.parseInt(finalM.getValue()) <= 0) {
                part++;
            }
        }
        int all = finalDays.size();
        percentage = part * 100 / all;
        return percentage;
    }

    //Average number of unused vacation days among all users

    @RequestMapping(value = {"/average"}, method = RequestMethod.GET)
    public Double average() throws ParseException {
        Map<String, String> vacationDays = totalVacationDaysService.findTotalVacationDaysByYearForAll(2021);
        Map<String, String> usedDays = usedVacationDaysService.numberOfVacationDatesForAllOneYear(String.valueOf(2021));
        List<Integer> finalDays = new ArrayList<>();
        for (Map.Entry<String, String> vacation : vacationDays.entrySet()) {
            for (Map.Entry<String, String> used : usedDays.entrySet()) {
                if (vacation.getKey().equals(used.getKey())) {
                    int to = Integer.parseInt(vacation.getValue()) - Integer.parseInt(used.getValue());
                    finalDays.add(to);
                }
            }
        }
        return finalDays.stream().mapToInt(a -> a).average().orElse(0);
    }

    //Table containing the number of used vacation days for each month among all users

    @RequestMapping(value = {"/byMonth"}, method = RequestMethod.GET)
    public Map<String, String> byMonth() throws ParseException {
        return usedVacationDaysService.numberOfVacationDatesByMonthForAll(String.valueOf(2021));
    }

    /*IF NEEDS---------------------------------------------------------------------------------------------

    //return number of unused days for every employee in 2021

    @RequestMapping(value = {"/all"}, method = RequestMethod.GET)
    public Map<String, String> all() throws ParseException {
        Map<String, String> vacationDays = totalVacationDaysService.findTotalVacationDaysByYearForAll(2021);
        Map<String, String> usedDays = usedVacationDaysService.numberOfVacationDatesForAllOneYear(String.valueOf(2021));
        Map<String, String> finalDays = new HashMap<>();
        for (Map.Entry<String, String> vacation : vacationDays.entrySet()) {
            for (Map.Entry<String, String> used : usedDays.entrySet()) {
                if (vacation.getKey().equals(used.getKey())) {
                    int to = Integer.parseInt(vacation.getValue()) - Integer.parseInt(used.getValue());
                    finalDays.put(vacation.getKey(), String.valueOf(to));
                }
            }
        }
        return finalDays;
    }*/
}
