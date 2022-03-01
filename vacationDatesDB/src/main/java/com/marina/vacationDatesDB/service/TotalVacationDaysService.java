package com.marina.vacationDatesDB.service;

import com.marina.vacationDatesDB.model.TotalVacationDays;
import com.marina.vacationDatesDB.repository.TotalVacationDaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class TotalVacationDaysService {
    @Autowired
    TotalVacationDaysRepository totalVacationDaysRepository;

    public List<TotalVacationDays> findTotalVacationDays() {
        return totalVacationDaysRepository.findAll();
    }
    public List<String> findAllEmployees() {
        return totalVacationDaysRepository.findAllEmployees();
    }
    public List <Integer> findTotalVacationDaysByYear(Integer year){
        return totalVacationDaysRepository.findTotalVacationDaysByYear(year);
    }

}