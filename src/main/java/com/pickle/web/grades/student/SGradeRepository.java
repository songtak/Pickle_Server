package com.pickle.web.grades.student;


import com.pickle.web.grades.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SGradeRepository extends JpaRepository<Grade, Long>, ISGradeRepository {
    @Query(value="select * from grade where user_code = :userCode", nativeQuery=true)
    List<Grade> getStudentGrade();


    @Query(value = "select score from grade where user_code = :userCode\n" +
            " and semester_code=(select max(semester_code) from grade) "
            , nativeQuery=true)
    int[] findUserScore(@Param("userCode") int userCode);



    @Query(value="select avg(score) from grade where semester_code=(select max(semester_code) from grade) and subject_id like :subjectId"
            , nativeQuery=true)
    double getMainChart(@Param("subjectId") int subjectId);



    @Query(value="select score from grade where user_code = :userCode and semester_code = :semesterCode  and subject_id = :subjectId",nativeQuery=true)
    int searchUserScore(@Param("userCode") int userCode, @Param("semesterCode") int semesterCode, @Param("subjectId") int subjectId);


    @Query(value="select avg(score) from grade where semester_code = :semesterCode  and subject_id = :subjectId",nativeQuery=true)
    int searchTotalScore(int semesterCode, int subjectId);


    @Query(value="select score from grade where user_code = :userCode and subject_id = 1\n" +
            "                           or user_code = :userCode and subject_id = 10\n" +
            "                           or user_code = :userCode and subject_id = 19;",nativeQuery=true)
    int[] getKorGrade(@Param("userCode") int userCode);



    @Query(value="select score from grade where user_code = :userCode and subject_id = 2\n" +
            "                           or user_code = :userCode and subject_id = 11\n" +
            "                           or user_code = :userCode and subject_id = 20;",nativeQuery=true)
    int[] getEngGrade(@Param("userCode") int userCode);


    @Query(value="select score from grade where user_code = :userCode and subject_id = 3\n" +
            "                           or user_code = :userCode and subject_id = 12\n" +
            "                           or user_code = :userCode and subject_id = 21;",nativeQuery=true)
    int[] getMatGrade(@Param("userCode") int userCode);


    @Query(value="select score from grade where user_code = :userCode and subject_id = 4\n" +
            "                           or user_code = :userCode and subject_id = 13\n" +
            "                           or user_code = :userCode and subject_id = 22;",nativeQuery=true)
    int[] getHisGrade(@Param("userCode") int userCode);


    @Query(value="select score from grade where user_code = :userCode and subject_id = 5\n" +
            "                           or user_code = :userCode and subject_id = 14\n" +
            "                           or user_code = :userCode and subject_id = 23;",nativeQuery=true)
    int[] getPhlGrade(@Param("userCode") int userCode);


    @Query(value="select score from grade where user_code = :userCode and subject_id = 6\n" +
            "                           or user_code = :userCode and subject_id = 15\n" +
            "                           or user_code = :userCode and subject_id = 24;",nativeQuery=true)
    int[] getEcoGrade(@Param("userCode") int userCode);


    @Query(value="select score from grade where user_code = :userCode and subject_id = 7\n" +
            "                           or user_code = :userCode and subject_id = 16\n" +
            "                           or user_code = :userCode and subject_id = 25;",nativeQuery=true)
    int[] getPhyGrade(@Param("userCode") int userCode);


    @Query(value="select score from grade where user_code = :userCode and subject_id = 8\n" +
            "                           or user_code = :userCode and subject_id = 17\n" +
            "                           or user_code = :userCode and subject_id = 26;",nativeQuery=true)
    int[] getBioGrade(@Param("userCode") int userCode);


    @Query(value="select score from grade where user_code = :userCode and subject_id = 9\n" +
            "                           or user_code = :userCode and subject_id = 18\n" +
            "                           or user_code = :userCode and subject_id = 27;",nativeQuery=true)
    int[] getForGrade(@Param("userCode") int userCode);

}
