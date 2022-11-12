package com.marina.vacationDatesDB.service;

import com.marina.vacationDatesDB.model.UsedVacationDays;
import com.marina.vacationDatesDB.repository.UsedVacationDaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UsedVacationDaysService {
    @Autowired
    UsedVacationDaysRepository usedVacationDaysRepository;

    //FIND NUMBER OF USED VACATION DATES FOR ALL EMPLOYEES FOR ONE YEAR

    public Map<String, String> numberOfVacationDatesForAllOneYear(String year) throws ParseException {
        int totalnumberOfDays = 0;
        int lastYear = Integer.parseInt(year)-1;
        Calendar calendar1 = new GregorianCalendar(lastYear, Calendar.JUNE,30);
        Calendar calendar2 = new GregorianCalendar(Integer.parseInt(year), Calendar.JUNE,30);
        Map<String, String> map = new HashMap<>();
        Date date1 = null;
        Date date2 = null;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        List<UsedVacationDays> listusers = usedVacationDaysRepository.findAll();
        for (UsedVacationDays user : listusers) {
            UsedVacationDays usedVacationDays = new UsedVacationDays(
                    user.getEmployee(),
                    user.getVacationStartDate(),
                    user.getVacationEndDate()
            );
            DateFormat df = new SimpleDateFormat(" MMMM dd, yyyy");
            String[] dateListStart = usedVacationDays.getVacationStartDate().split(",");
            String[] dateListEnd = usedVacationDays.getVacationEndDate().split(",");
            if (dateListStart[2].trim().equals(String.valueOf(lastYear)) || dateListEnd[2].trim().equals(year)) {
                date1 = df.parse(dateListStart[1] + "," + dateListStart[2]);
                date2 = df.parse(dateListEnd[1] + "," + dateListEnd[2]);
                cal1.setTime(date1);
                cal2.setTime(date2);

                int numberOfDays = 0;

                while (cal1.before(cal2)  || cal1.equals(cal2)) {
                    if(cal1.after(calendar1) && cal2.before(calendar2)){
                        if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
                                && (Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
                            numberOfDays++;
                        }}
                    cal1.add(Calendar.DATE, 1);
                }
                if(!(map.containsKey(user.getEmployee()))){
                    map.put(user.getEmployee(), String.valueOf(totalnumberOfDays));
                }
                else{
                    int oldvalue = Integer.parseInt(map.get(user.getEmployee()));
                    int newvlaue = oldvalue + numberOfDays;
                    map.put(user.getEmployee(), String.valueOf(newvlaue));
                }
            }

        }
        return map;
    }

    //FIND NUMBER OF USED VACATION DATES BY MONTH FOR ALL EMPLOYEES

    public Map<String,String> numberOfVacationDatesByMonthForAll(String year) throws ParseException {
        Map<String,String> map = new HashMap<>();
        Date date1 = null;
        Date date2 = null;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        int nJ,nF,nMr,nAp,nMy,nJu,nJl,nAv,nS,nO,nN,nD;
        nJ = nF = nMr = nAp =nMy = nJu = nJl = nAv = nS = nO = nN = nD = 0;
        List<UsedVacationDays> listusers = usedVacationDaysRepository.findAll();
        for (UsedVacationDays user : listusers) {
            UsedVacationDays usedVacationDays = new UsedVacationDays(
                    user.getVacationStartDate(),
                    user.getVacationEndDate()
            );
            DateFormat df = new SimpleDateFormat(" MMMM dd, yyyy");
            String[] dateListStart = usedVacationDays.getVacationStartDate().split(",");
            String[] dateListEnd = usedVacationDays.getVacationEndDate().split(",");
            if (dateListStart[2].trim().equals(year) || dateListEnd[2].trim().equals(year)) {
                date1 = df.parse(dateListStart[1] + "," + dateListStart[2]);
                date2 = df.parse(dateListEnd[1] + "," + dateListEnd[2]);
                cal1.setTime(date1);
                cal2.setTime(date2);



                while (cal1.before(cal2) || cal1.equals(cal2)) {
                    if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
                            && (Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
                        switch (cal1.get(Calendar.MONTH)) {
                            case Calendar.JANUARY:
                                nJ++;
                                break;
                            case Calendar.FEBRUARY:
                                nF++;
                                break;
                            case Calendar.MARCH:
                                nMr++;
                                break;
                            case Calendar.APRIL:
                                nAp++;
                                break;
                            case Calendar.MAY:
                                nMy++;
                                break;
                            case Calendar.JUNE:
                                nJu++;
                                break;
                            case Calendar.JULY:
                                nJl++;
                                break;
                            case Calendar.AUGUST:
                                nAv++;
                                break;
                            case Calendar.SEPTEMBER:
                                nS++;
                                break;
                            case Calendar.OCTOBER:
                                nO++;
                                break;
                            case Calendar.NOVEMBER:
                                nN++;
                                break;
                            case Calendar.DECEMBER:
                                nD++;
                                break;
                        }
                    }
                    cal1.add(Calendar.DATE, 1);
                }
            }
        }
        map.put("January", String.valueOf(nJ));
        map.put("February", String.valueOf(nF));
        map.put("March", String.valueOf(nMr));
        map.put("April", String.valueOf(nAp));
        map.put("May", String.valueOf(nMy));
        map.put("June", String.valueOf(nJu));
        map.put("July", String.valueOf(nJl));
        map.put("August", String.valueOf(nAv));
        map.put("September", String.valueOf(nS));
        map.put("October", String.valueOf(nO));
        map.put("November", String.valueOf(nN));
        map.put("December", String.valueOf(nD));

        return map;
    }


    //ALL VACATIONS FOR ONE EMPLOYEE

    public List <UsedVacationDays> findUsedVacationDaysByEmployee(String employee){
        return usedVacationDaysRepository.findUsedVacationDaysByEmployee(employee);
    }

    //TOTAL NUMBER OF USED VACATION DAYS FOR ONE EMPLOYEE

    public List <Integer> findUsedVacationDaysByEmployeeAndHowLong(String employee) throws ParseException {
        List<Integer> ukupno = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        Date date1 = null;
        Date date2 = null;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        List<UsedVacationDays> listusers = usedVacationDaysRepository.findUsedVacationDaysByEmployee(employee);
        for (UsedVacationDays user : listusers) {
            UsedVacationDays usedVacationDays = new UsedVacationDays(
                    user.getEmployee(),
                    user.getVacationStartDate(),
                    user.getVacationEndDate()
            );
            DateFormat df = new SimpleDateFormat(" MMMM dd, yyyy");
            String[] dateListStart = usedVacationDays.getVacationStartDate().split(",");
            String[] dateListEnd = usedVacationDays.getVacationEndDate().split(",");
            date1 = df.parse(dateListStart[1] + "," + dateListStart[2]);
            date2 = df.parse(dateListEnd[1] + "," + dateListEnd[2]);
            cal1.setTime(date1);
            cal2.setTime(date2);

            int numberOfDays = 0;

            while (cal1.before(cal2)  || cal1.equals(cal2)) {
                if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
                        && (Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
                    numberOfDays++;
                }
                cal1.add(Calendar.DATE, 1);
            }
            ukupno.add(numberOfDays);

        }
        return ukupno;
    }

    //FIND NUMBER OF USED VACATION DATES FOR ONE EMPLOYEE IN ONE YEAR

    public Map<String,String> findTotalUsedDaysInYearForOne(@PathVariable("employee") String employee, String year) throws ParseException {
        int totalnumberOfDays = 0;
        int lastYear = Integer.parseInt(year)-1;
        Calendar calendar1 = new GregorianCalendar(lastYear, Calendar.JUNE,30);
        Calendar calendar2 = new GregorianCalendar(Integer.parseInt(year), Calendar.JUNE,30);
        List<UsedVacationDays> listUsedDays = usedVacationDaysRepository.findUsedVacationDaysByEmployee(employee);
        for (UsedVacationDays usedDays : listUsedDays) {
            UsedVacationDays usedVacationDays = new UsedVacationDays(
                    usedDays.getVacationStartDate(),
                    usedDays.getVacationEndDate()
            );
            DateFormat df = new SimpleDateFormat(" MMMM dd, yyyy");
            Date date1 = null;
            Date date2 = null;
            String[] dateListStart = usedVacationDays.getVacationStartDate().split(",");
            String[] dateListEnd = usedVacationDays.getVacationEndDate().split(",");
            if (dateListStart[2].trim().equals(String.valueOf(lastYear)) || dateListEnd[2].trim().equals(year)) {
                date1 = df.parse(dateListStart[1] + "," + dateListStart[2]);
                date2 = df.parse(dateListEnd[1] + "," + dateListEnd[2]);
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(date1);
                cal2.setTime(date2);

                int numberOfDays = 0;

                while (cal1.before(cal2)  || cal1.equals(cal2)) {
                    if(cal1.after(calendar1) && cal2.before(calendar2)){
                        if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
                                && (Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
                            numberOfDays++;
                        }}
                    cal1.add(Calendar.DATE, 1);
                }
                totalnumberOfDays = totalnumberOfDays + numberOfDays;
            }
        }
        Map<String,String> map = new HashMap<>();
        map.put(year, String.valueOf(totalnumberOfDays));
        return map;
    }

    //FIND NUMBER OF USED VACATION DATES BY MONTH FOR ONE EMPLOYEE

    public Map<String,String> numberOfVacationDatesByMonthForOne(String employee, String year) throws ParseException {
        Map<String,String> map = new HashMap<>();
        Date date1 = null;
        Date date2 = null;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        int nJ,nF,nMr,nAp,nMy,nJu,nJl,nAv,nS,nO,nN,nD;
        nJ = nF = nMr = nAp =nMy = nJu = nJl = nAv = nS = nO = nN = nD = 0;
        List<UsedVacationDays> listusers = usedVacationDaysRepository.findUsedVacationDaysByEmployee(employee);
        for (UsedVacationDays user : listusers) {
            UsedVacationDays usedVacationDays = new UsedVacationDays(
                    user.getVacationStartDate(),
                    user.getVacationEndDate()
            );
            DateFormat df = new SimpleDateFormat(" MMMM dd, yyyy");
            String[] dateListStart = usedVacationDays.getVacationStartDate().split(",");
            String[] dateListEnd = usedVacationDays.getVacationEndDate().split(",");
            if (dateListStart[2].trim().equals(year) || dateListEnd[2].trim().equals(year)) {
                date1 = df.parse(dateListStart[1] + "," + dateListStart[2]);
                date2 = df.parse(dateListEnd[1] + "," + dateListEnd[2]);
                cal1.setTime(date1);
                cal2.setTime(date2);



                while (cal1.before(cal2) || cal1.equals(cal2)) {
                    if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
                            && (Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
                        switch (cal1.get(Calendar.MONTH)) {
                            case Calendar.JANUARY:
                                nJ++;
                                break;
                            case Calendar.FEBRUARY:
                                nF++;
                                break;
                            case Calendar.MARCH:
                                nMr++;
                                break;
                            case Calendar.APRIL:
                                nAp++;
                                break;
                            case Calendar.MAY:
                                nMy++;
                                break;
                            case Calendar.JUNE:
                                nJu++;
                                break;
                            case Calendar.JULY:
                                nJl++;
                                break;
                            case Calendar.AUGUST:
                                nAv++;
                                break;
                            case Calendar.SEPTEMBER:
                                nS++;
                                break;
                            case Calendar.OCTOBER:
                                nO++;
                                break;
                            case Calendar.NOVEMBER:
                                nN++;
                                break;
                            case Calendar.DECEMBER:
                                nD++;
                                break;
                        }
                    }
                    cal1.add(Calendar.DATE, 1);
                }
            }
        }
        map.put("January", String.valueOf(nJ));
        map.put("February", String.valueOf(nF));
        map.put("March", String.valueOf(nMr));
        map.put("April", String.valueOf(nAp));
        map.put("May", String.valueOf(nMy));
        map.put("June", String.valueOf(nJu));
        map.put("July", String.valueOf(nJl));
        map.put("August", String.valueOf(nAv));
        map.put("September", String.valueOf(nS));
        map.put("October", String.valueOf(nO));
        map.put("November", String.valueOf(nN));
        map.put("December", String.valueOf(nD));

        return map;
    }

    //IF NEED--------------------------------------------------------------------------------------------------

    //TOTAL NUMBER OF VACATION DAYS BY YEAR FOR ALL

    public Integer totalNumberOfUsedVacationByYearForAll(String year) throws ParseException {
        int totalnumberOfDays = 0;
        Date date1 = null;
        Date date2 = null;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        List<UsedVacationDays> listusers = usedVacationDaysRepository.findAll();
        for (UsedVacationDays user : listusers) {
            UsedVacationDays usedVacationDays = new UsedVacationDays(
                    user.getVacationStartDate(),
                    user.getVacationEndDate()
            );
            DateFormat df = new SimpleDateFormat(" MMMM dd, yyyy");
            String[] dateListStart = usedVacationDays.getVacationStartDate().split(",");
            String[] dateListEnd = usedVacationDays.getVacationEndDate().split(",");
            if (dateListStart[2].trim().equals(year) || dateListEnd[2].trim().equals(year)) {
                date1 = df.parse(dateListStart[1] + "," + dateListStart[2]);
                date2 = df.parse(dateListEnd[1] + "," + dateListEnd[2]);
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
}
