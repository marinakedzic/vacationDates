package com.marina.CSVtoDB.model;

import javax.persistence.*;

@Entity
@Table(name = "totalVacationDays")
public class TotalVacationDays {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "employee")
    private String employee;

    @Column(name = "vacationDays")
    private int vacationDays;

    @Column(name = "year")
    private int year;

    public TotalVacationDays() {

    }

    public TotalVacationDays(String employee, int  vacationDays, int year) {
        this.employee = employee;
        this.vacationDays = vacationDays;
        this.year = year;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public int getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(int vacationDays) {
        this.vacationDays = vacationDays;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}