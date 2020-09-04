package com.pickle.web.grades;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pickle.web.subjects.Subject;
import com.pickle.web.subjects.SubjectDetail;
import com.pickle.web.users.User;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@ToString
@Table(name = "grade")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id", nullable = false) private Long id;
    @Column(name = "semester_code", nullable = false) private int semesterCode;
    @Column(name = "score", nullable = false) private int score;

    public Grade(){}
    @Builder
    public Grade(int semesterCode,
                 int score) {
        this.semesterCode = semesterCode;
        this.score = score;
    }


    @ManyToOne @JoinColumn(name = "user_code", referencedColumnName = "user_code") @JsonIgnore
    private User user;
    @ManyToOne @JoinColumn(name = "subject_id") @JsonIgnore
    private Subject subject;

}