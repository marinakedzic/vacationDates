package com.marina.CSVtoDB.repository;

import com.marina.CSVtoDB.model.TotalVacationDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotalVacationDaysRepository extends JpaRepository<TotalVacationDays, Integer>{
}
