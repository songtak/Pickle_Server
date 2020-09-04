package com.pickle.web.attendances.student;



import com.pickle.web.attendances.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SAttendanceRepository extends JpaRepository<Attendance, Long>, ISAttendanceRepository{

    @Query(value="select * from attendance a where a.user_code = :userCode\n" +
            "and a.date between '2020-00-00 00:00:00' and '2021-00-00 00:00:00' ", nativeQuery = true)
    List<Object> findStudentAtte(@Param("userCode") int userCode);


    @Query(value="select sum(a.present), sum(a.absent), sum(a.tardy) from attendance a where a.user_code = :userCode\n" +
            "and a.date between '2020-00-00 00:00:00' and '2021-00-00 00:00:00'", nativeQuery = true)
    List<Object> findStudentAtteSum(@Param("userCode")int userCode);
}