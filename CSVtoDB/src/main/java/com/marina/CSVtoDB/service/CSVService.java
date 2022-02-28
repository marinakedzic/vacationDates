package com.marina.CSVtoDB.service;

import com.marina.CSVtoDB.model.TotalVacationDays;
import com.marina.CSVtoDB.model.UsedVacationDays;
import com.marina.CSVtoDB.repository.TotalVacationDaysRepository;
import com.marina.CSVtoDB.repository.UsedVacationDaysRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
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
public class CSVService {
    @Autowired
    TotalVacationDaysRepository totalVacationDaysRepository;
    @Autowired
    UsedVacationDaysRepository usedVacationDaysRepository;

    public void saveTotalVacationDays(MultipartFile file) {
        try {
            List<TotalVacationDays> totalVacationDays = csvToTotalVacationDays(file.getInputStream());
            totalVacationDaysRepository.saveAll(totalVacationDays);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }
    public void saveUsedVacationDays(MultipartFile file) {
        try {
            List<UsedVacationDays> usedVacationDays = csvToUsedVacationDays(file.getInputStream());
            usedVacationDaysRepository.saveAll(usedVacationDays);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public static String TYPE = "text/csv";
    static String[] HEADERs = { "Employee", "Total vacation date" };

    public static boolean hasCSVFormat(MultipartFile file) {
        if (TYPE.equals(file.getContentType())
                || file.getContentType().equals("application/vnd.ms-excel")) {
            return true;
        }
        return false;
    }

    public static List<TotalVacationDays> csvToTotalVacationDays(InputStream is) {
        int year = 0;
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withHeader(HEADERs).withIgnoreHeaderCase().withTrim());) {

            List<TotalVacationDays> totalVacationDaysList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                if(csvRecord.getRecordNumber()==1){
                    continue;
                }
                TotalVacationDays totalVacationDays = new TotalVacationDays(
                        csvRecord.get("Employee"),
                        Integer.parseInt(csvRecord.get("Total vacation date")),
                        year
                );

                totalVacationDaysList.add(totalVacationDays);
            }

            return totalVacationDaysList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static List<UsedVacationDays> csvToUsedVacationDays(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<UsedVacationDays> usedVacationDaysList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                if(csvRecord.getRecordNumber()==1){
                    continue;
                }
                UsedVacationDays usedVacationDays = new UsedVacationDays(
                        csvRecord.get("Employee"),
                        csvRecord.get("Vacation start date"),
                        csvRecord.get("Vacation end date")
                );

                usedVacationDaysList.add(usedVacationDays);
            }

            return usedVacationDaysList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

}