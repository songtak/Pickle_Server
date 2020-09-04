package com.pickle.web.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.web.attendances.Attendance;
import com.pickle.web.chatbot.Chat;
import com.pickle.web.chatbot.ExamAnalysis;
import com.pickle.web.file.File;
import com.pickle.web.grades.Grade;
import com.pickle.web.grades.Mock;
import com.pickle.web.notices.Notice;
import com.pickle.web.notices.NoticeComment;
import com.pickle.web.subjects.Subject;
import com.pickle.web.subjects.SubjectDetail;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Component
@NoArgsConstructor
@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = "user_code")})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_code", nullable = false) @NaturalId
    private int userCode;
    @Column(name = "school_code", nullable = false)
    private int schoolCode;
    @Column(name = "school_name", nullable = false)
    private String schoolName;
    @Column(name = "cur_grade")
    private int curGrade;
    @Column(name = "home_class")
    private int homeClass;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "user_id", nullable = false)
    private String userId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "user_pw", nullable = false)
    private String userPw;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "position_checker", columnDefinition = "boolean default 0")
    private int positionChecker;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) @JsonIgnore
    private List<Grade> grades;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) @JsonIgnore
    private List<Mock> mocks;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) @JsonIgnore
    private List<Attendance> attendances;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) @JsonIgnore
    private List<Notice> notices;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) @JsonIgnore
    private List<NoticeComment> noticeComments;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) @JsonIgnore
    private List<SubjectDetail> subjectsDetails;
}