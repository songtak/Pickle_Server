package com.pickle.web.schedules;

import com.querydsl.core.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long>, IScheduleRepository{
    @Query(value = "select mon, tue, wed, thu, fri from schedule where checker= :checker", nativeQuery = true)
    List<Object> SUserTimetable(@Param("checker") int checker);
}
