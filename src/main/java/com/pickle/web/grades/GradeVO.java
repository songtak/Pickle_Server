package com.pickle.web.grades;

import lombok.Data;

@Data
public class GradeVO {
    private Long gradeId;
    private int semesterCode;
    private int score;
    private Long subjectId;
    private String subjectName;
    private int userCode;
    private String name;
}
