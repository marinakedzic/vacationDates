package com.marina.CSVtoDB.model;

import javax.persistence.*;

@Entity
@Table(name = "usedVacationDays")
public class UsedVacationDays {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "employee")
    private String employee;

    @Column(name = "vacationStartDate")
    private String vacationStartDate;

    @Column(name = "vacationEndDate")
    private String vacationEndDate;

    public UsedVacationDays() {

    }

    public UsedVacationDays(String employee, String  vacationStartDate, String vacationEndDate) {
        this.employee = employee;
        this.vacationStartDate = vacationStartDate;
        this.vacationEndDate = vacationEndDate;
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

    public String getVacationStartDate() {
        return vacationStartDate;
    }

    public void setVacationStartDate(String vacationStartDate) {
        this.vacationStartDate = vacationStartDate;
    }

    public String getVacationEndDate() {
        return vacationEndDate;
    }

    public void setVacationEndDate(String vacationEndDate) {
        this.vacationEndDate = vacationEndDate;
    }
}
