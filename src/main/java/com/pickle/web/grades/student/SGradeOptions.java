package com.pickle.web.grades.student;

import lombok.Data;


@Data
public class SGradeOptions {
    private int selectedGrade;
    private int[] subjectList; //들어올 때
    private String[] examList; // 들어올 때
    private int[] userSelectedList;
}
