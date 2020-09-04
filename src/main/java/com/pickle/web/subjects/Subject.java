package com.pickle.web.subjects;

import com.pickle.web.file.File;
import com.pickle.web.grades.Grade;
import com.pickle.web.users.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter @Component
@NoArgsConstructor
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;

    @Column(name = "subject_code", nullable = false)
    private String subjectCode;

    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Grade> grades;
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<File> files;
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<SubjectDetail> subjectDetails;

}
