package com.marina.vacationDates.controller;

import com.marina.vacationDates.model.UsedVacationDays;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeDetailsController {

    @GetMapping("/vacations/{employee}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')" + " || (hasAuthority('ROLE_USER') && #employee == authentication.principal.username)")
    public ModelAndView employeeVacations (@PathVariable("employee") String employee){

        //Total number of vacation days for each year

        RestTemplate restTemplate5 = new RestTemplate();
        ResponseEntity<Map> response5 =
                restTemplate5.getForEntity(
                        "http://localhost:8081/employee/totalVacationDaysByUser/" + employee,
                        Map.class);
        Map totalVacationDaysByUser = response5.getBody();

        //Total number of unused vacation days for each year

        RestTemplate restTemplate6 = new RestTemplate();
        ResponseEntity<Map> response6 =
                restTemplate6.getForEntity(
                        "http://localhost:8081/employee/totalUnusedDaysByUser/" + employee,
                        Map.class);
        Map totalUnusedDaysByUser = response6.getBody();

        //Number of vacation days used for each month

        RestTemplate restTemplate7 = new RestTemplate();
        ResponseEntity<Map> response7 =
                restTemplate7.getForEntity(
                        "http://localhost:8081/employee/byMonth/" + employee,
                        Map.class);
        Map byMonth = response7.getBody();

        //Dates when the user took vacation days and for how long

        RestTemplate restTemplate8 = new RestTemplate();
        ResponseEntity<UsedVacationDays[]> response8 =
                restTemplate8.getForEntity(
                        "http://localhost:8081/employee/when/" + employee,
                        UsedVacationDays[].class);
        UsedVacationDays[] when = response8.getBody();

        RestTemplate restTemplate9 = new RestTemplate();
        ResponseEntity<List> response9 =
                restTemplate9.getForEntity(
                        "http://localhost:8081/employee/howLong/" + employee,
                        List.class);
        List howLong = response9.getBody();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("employee", employee);
        modelAndView.addObject("totalVacationDaysByUser", totalVacationDaysByUser);
        modelAndView.addObject("totalUnusedDaysByUser", totalUnusedDaysByUser);
        modelAndView.addObject("byMonth", byMonth);
        modelAndView.addObject("when", when);
        modelAndView.addObject("howLong", howLong);
        modelAndView.setViewName("employeeDetails");
        return modelAndView;
    }
}
