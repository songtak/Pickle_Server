package com.pickle.web.attendances.teacher;


import com.pickle.web.attendances.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TAttendanceRepository extends JpaRepository<Attendance, Long>,IAttendanceRepository {


}
