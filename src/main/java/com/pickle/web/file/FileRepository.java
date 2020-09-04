package com.pickle.web.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long>, IFileRepository {
    public Optional<File> findById(Long fileId);
    @Query(value = "select * from file f left outer join subject s on f.subject_id like s.subject_id where s.subject_id like :subjectId", nativeQuery = true)
    List<File> findBySubjectId(@Param("subjectId") Long subjectId);
}
