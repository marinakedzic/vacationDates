package com.marina.CSVtoDB.repository;

import com.marina.CSVtoDB.model.UsedVacationDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsedVacationDaysRepository extends JpaRepository<UsedVacationDays, Integer>{
}
