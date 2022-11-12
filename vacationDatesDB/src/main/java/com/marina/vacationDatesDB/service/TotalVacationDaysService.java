package com.marina.vacationDatesDB.service;

import com.marina.vacationDatesDB.model.TotalVacationDays;
import com.marina.vacationDatesDB.repository.TotalVacationDaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TotalVacationDaysService {
    @Autowired
    TotalVacationDaysRepository totalVacationDaysRepository;

    //FIND ALL EMPLOYEES

    public List<TotalVacationDays> findAllEmployees() {
        return totalVacationDaysRepository.findAllEmployees();
    }

    //FIND NUMBER OF VACATION DAYS FOR ALL EMPLOYEES BY YEAR

    public Map<String, String> findTotalVacationDaysByYearForAll(int year) {
        Map<String, String> map = new HashMap<>();
        List<TotalVacationDays> listUsersa = totalVacationDaysRepository.findAllEmployees();
        for(TotalVacationDays users: listUsersa) {
            TotalVacationDays userDays = totalVacationDaysRepository.findTotalVacationDaysByEmployeeAndYear(users.getEmployee(), year);
            if(userDays!=null){
                map.put(users.getEmployee(), String.valueOf(userDays.getVacationDays()));}}
        return map;
    }

    //FIND NUMBER OF VACATION DAYS BY EMPOLYEE

    public Map<String, String> findTotalVacationDaysByEmployee(String employee){
        List <TotalVacationDays> lista = totalVacationDaysRepository.findTotalVacationDaysByEmployee(employee);
        Map<String, String> map = new HashMap<>();
        for (TotalVacationDays user : lista) {
            map.put(String.valueOf(user.getYear()), String.valueOf(user.getVacationDays()));
        }
        return map;
    }




    //IF NEED -------------------------------------------------------------------------------------------

    // FIND TOTAL NUMBER OF ALL VACATION DAYS FOR ALL BY YEAR

    public Integer findTotalVacationDaysByYearsForAll(int year) {
        int totalnumberOfDays = 0;
        List<TotalVacationDays> listusers = totalVacationDaysRepository.findTotalVacationDaysByYear(year);
        for (TotalVacationDays user : listusers) {
            totalnumberOfDays += user.getVacationDays();
        }
        return totalnumberOfDays;
    }

    //in totalvacationcontroller

    //FIND NUMBER OF VACATION DAYS BY EMPLOYEE AND YEAR

    public Map<String, String> findTotalVacationDaysByEmployeeAndYear(String employee, int year) {
        Map<String, String> map = new HashMap<>();
        TotalVacationDays userDays = totalVacationDaysRepository.findTotalVacationDaysByEmployeeAndYear(employee, year);
        if(userDays!=null){
            map.put(employee, String.valueOf(userDays.getVacationDays()));}
        return map;
    }
}