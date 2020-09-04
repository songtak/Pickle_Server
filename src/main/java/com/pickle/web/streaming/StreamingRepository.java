package com.pickle.web.streaming;

import com.pickle.web.schedules.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StreamingRepository extends JpaRepository<Schedule, Long>, IStreamingRepository{
}
