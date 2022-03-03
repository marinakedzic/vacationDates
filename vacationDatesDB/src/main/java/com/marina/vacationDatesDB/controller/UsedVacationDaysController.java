package com.marina.vacationDatesDB.controller;

import com.marina.vacationDatesDB.model.TotalVacationDays;
import com.marina.vacationDatesDB.model.UsedVacationDays;
import com.marina.vacationDatesDB.service.TotalVacationDaysService;
import com.marina.vacationDatesDB.service.UsedVacationDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/usedDays")
public class UsedVacationDaysController {

    @Autowired
    UsedVacationDaysService usedVacationDaysService;
    @Autowired
    TotalVacationDaysService totalVacationDaysService;

    @RequestMapping(value = {"/{employee}"}, method = RequestMethod.GET)
    public List<UsedVacationDays> findTotalVacationDaysByYear(@PathVariable("employee") String employee){
        List<UsedVacationDays> listusers = usedVacationDaysService.findUsedVacationDaysByEmployee(employee);
        return listusers;
    }
    //return total unused days for 2021
    @RequestMapping(value = {"/totalUnusedDays"}, method = RequestMethod.GET)
    public Integer findtotalUnusedDays() throws ParseException {
        int totalnumberOfDays = 0;
        List<UsedVacationDays> listusers = usedVacationDaysService.findtotalUnusedDays();
        for (UsedVacationDays user : listusers) {
            UsedVacationDays usedVacationDays = new UsedVacationDays(
                    user.getVacationStartDate(),
                    user.getVacationEndDate()
            );
            DateFormat df = new SimpleDateFormat(" MMMM dd, yyyy");
            Date date1 = null;
            Date date2 = null;
            String[] dateListStart = usedVacationDays.getVacationStartDate().split(",");
            String[] dateListEnd = usedVacationDays.getVacationEndDate().split(",");
            if (dateListStart[2].trim().equals("2021") || dateListEnd[2].trim().equals("2021")) {
                date1 = df.parse(dateListStart[1] + "," + dateListStart[2]);
                date2 = df.parse(dateListEnd[1] + "," + dateListEnd[2]);
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(date1);
                cal2.setTime(date2);

                int numberOfDays = 0;

                while (cal1.before(cal2) || cal1.equals(cal2)) {
                    if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
                            && (Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
                        numberOfDays++;
                    }
                    cal1.add(Calendar.DATE, 1);
                }
                totalnumberOfDays = totalnumberOfDays + numberOfDays;

            }
        }
        return totalnumberOfDays;
    }

    //It is not finished
    @RequestMapping(value = {"/precentageUsers"}, method = RequestMethod.GET)
    public Double findPrecentage() throws ParseException {
        int totalnumberOfDays = 0;
        int totalusedOfDays = 0;
        int userNumber = 0;
        double precentage = 0.0;
        List<TotalVacationDays> listTotalDays = totalVacationDaysService.findTotalVacationDaysByYear(2021);
        for (TotalVacationDays user : listTotalDays) {
            TotalVacationDays totalVacationDays = new TotalVacationDays(
                    user.getEmployee(),
                    user.getVacationDays()
            );
            List<UsedVacationDays> listUsedVacationDays = usedVacationDaysService.findUsedVacationDaysByEmployee(user.getEmployee());
            for (UsedVacationDays user1 : listUsedVacationDays) {
                UsedVacationDays usedVacationDays = new UsedVacationDays(
                        user1.getVacationStartDate(),
                        user1.getVacationEndDate()
                );
                DateFormat df = new SimpleDateFormat(" MMMM dd, yyyy");
                Date date1;
                Date date2;
                String[] dateListStart = usedVacationDays.getVacationStartDate().split(",");
                String[] dateListEnd = usedVacationDays.getVacationEndDate().split(",");
                date1 = df.parse(dateListStart[1] + "," + dateListStart[2]);
                date2 = df.parse(dateListEnd[1] + "," + dateListEnd[2]);
                Date date3 = df.parse(" July 31, 2020");
                Date date4 = df.parse(" July 31, 2021");
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                Calendar cal3 = Calendar.getInstance();
                Calendar cal4 = Calendar.getInstance();
                cal1.setTime(date1);
                cal2.setTime(date2);
                cal3.setTime(date3);
                cal4.setTime(date4);

                int numberOfDays = 0;

                while (cal1.before(cal2) || cal1.equals(cal2)) {
                    if(cal1.after(cal3) && cal1.before(cal4)){
                        System.out.println(date1);
                        System.out.println(date2);
                        if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
                            && (Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
                        numberOfDays++;
                    }
                    cal1.add(Calendar.DATE, 1);
                }
                else{
                        System.out.println(date1);
                        System.out.println(date2);
                    }
                }
                totalnumberOfDays = totalnumberOfDays + numberOfDays;
            }

            totalusedOfDays = totalVacationDays.getVacationDays() - totalnumberOfDays;
            if(totalusedOfDays==0){
                userNumber++;
            }

            int allUser = listTotalDays.size();
            precentage = allUser / userNumber * 100;

        }

        return precentage;
    }



}