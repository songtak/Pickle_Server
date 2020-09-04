package com.pickle.web.subjects.teacher;


import com.pickle.web.subjects.SubjectDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TSubjectRepository extends JpaRepository<SubjectDetail, Long>, ISubjectRepository {
}
