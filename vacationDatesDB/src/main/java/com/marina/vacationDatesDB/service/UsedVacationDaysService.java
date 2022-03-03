package com.marina.vacationDatesDB.service;

import com.marina.vacationDatesDB.model.UsedVacationDays;
import com.marina.vacationDatesDB.repository.UsedVacationDaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsedVacationDaysService {
    @Autowired
    UsedVacationDaysRepository usedVacationDaysRepository;

    public List<UsedVacationDays> findUsedVacationDays() {
        return usedVacationDaysRepository.findAll();
    }
    public List <UsedVacationDays> findUsedVacationDaysByEmployee(String employee){
        return usedVacationDaysRepository.findUsedVacationDaysByEmployee(employee);
    }
    public List <UsedVacationDays> findtotalUnusedDays(){
        return usedVacationDaysRepository.findtotalUnusedDays();
    }

}
