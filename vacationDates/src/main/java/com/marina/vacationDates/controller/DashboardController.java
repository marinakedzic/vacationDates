package com.marina.vacationDates.controller;
import com.marina.vacationDates.model.TotalVacationDays;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import java.util.Map;

@RestController
@RequestMapping("/")
public class DashboardController {

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView total(){

        //Total number of unused vacation days among all users
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Integer> response =
                restTemplate.getForEntity(
                        "http://localhost:8081/dashboard/totalUnusedDays",
                        Integer.class);
        Integer totalUnusedDays = response.getBody();

        //Percentage of users that used all vacation days for 2021
        RestTemplate restTemplate2 = new RestTemplate();
        ResponseEntity<Integer> response2 =
                restTemplate2.getForEntity(
                        "http://localhost:8081/dashboard/percentage",
                        Integer.class);
        Integer percentage = response2.getBody();

        //Average number of unused vacation days among all users
        RestTemplate restTemplate3 = new RestTemplate();
        ResponseEntity<Double> response3 =
                restTemplate3.getForEntity(
                        "http://localhost:8081/dashboard/average",
                        Double.class);
        Double average = response3.getBody();

        //Table containing the number of used vacation days for each month among all users
        RestTemplate restTemplate4 = new RestTemplate();
        ResponseEntity<Map> response4 =
                restTemplate4.getForEntity(
                        "http://localhost:8081/dashboard/byMonth",
                        Map.class);
        Map byMonth = response4.getBody();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("totalUnusedDays", totalUnusedDays);
        modelAndView.addObject("percentage", percentage);
        modelAndView.addObject("average", average);
        modelAndView.addObject("byMonth", byMonth);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = {"/allUsers"}, method = RequestMethod.GET)
    public ModelAndView showUsers(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TotalVacationDays[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8081/employee/employees",
                        TotalVacationDays[].class);
        TotalVacationDays[] employees = response.getBody();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("employees", employees);
        modelAndView.setViewName("allUsers");
        return modelAndView;
    }
}
