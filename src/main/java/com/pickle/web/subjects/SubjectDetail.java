package com.pickle.web.subjects;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.pickle.web.attendances.Attendance;
import com.pickle.web.file.File;
import com.pickle.web.grades.Grade;
import com.pickle.web.users.User;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
@NoArgsConstructor
@Table(name = "subject_detail")
public class SubjectDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subjectdetail_id")
    private Long id;

    @Column(name = "lesson_no", nullable = false)
    private int lessonNo;

    @Column(name = "lesson_title", nullable = true)
    private String lessonTitle;

    @Column(name = "lesson_detail", nullable = true)
    private String lessonDetail;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "user_code", referencedColumnName = "user_code")
    @JsonIgnore
    private User user;
    @ManyToOne @JoinColumn(name = "subject_id")
    @JsonIgnore
    private Subject subject;

}