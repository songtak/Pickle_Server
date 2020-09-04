package com.pickle.web.subjects.teacher;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
public class SubjectDetailVO {
    private int userCode, lessonNo;
    private String lessonTitle, lessonDetail;
}
