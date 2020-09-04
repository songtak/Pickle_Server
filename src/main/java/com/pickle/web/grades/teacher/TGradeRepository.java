package com.pickle.web.grades.teacher;

import com.pickle.web.grades.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TGradeRepository extends JpaRepository<Grade, Long>, ITGradeRepository {
}
