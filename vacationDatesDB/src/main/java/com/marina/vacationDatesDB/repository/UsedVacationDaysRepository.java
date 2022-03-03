package com.marina.vacationDatesDB.repository;

import com.marina.vacationDatesDB.model.UsedVacationDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UsedVacationDaysRepository extends JpaRepository<UsedVacationDays, Integer>{
    @Query(value=" SELECT * FROM vacationdates.used_vacation_days"
            + " where vacationdates.used_vacation_days.employee=?1 ", nativeQuery=true)
    @Transactional
    List<UsedVacationDays> findUsedVacationDaysByEmployee (@Param("employee") String employee);

    @Query(value=" SELECT * FROM vacationdates.used_vacation_days", nativeQuery=true)
    @Transactional
    List<UsedVacationDays> findtotalUnusedDays ();

}
