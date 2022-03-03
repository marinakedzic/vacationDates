package com.marina.vacationDatesDB.repository;

import com.marina.vacationDatesDB.model.TotalVacationDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TotalVacationDaysRepository extends JpaRepository<TotalVacationDays, Integer>{
    @Query(value=" SELECT DISTINCT total_vacation_days.employee FROM vacationdates.total_vacation_days", nativeQuery=true)
    @Transactional
    List <String> findAllEmployees();
    @Query(value=" SELECT * FROM vacationdates.total_vacation_days"
            + " where total_vacation_days.year=?1 ", nativeQuery=true)
    @Transactional
    List<TotalVacationDays> findTotalVacationDaysByYear (@Param("year") Integer year);
}
