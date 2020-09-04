package com.pickle.web.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pickle.web.notices.Notice;
import com.pickle.web.subjects.Subject;
import com.pickle.web.subjects.SubjectDetail;
import com.pickle.web.users.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "extension")
    private String extension;
    @Column(name = "content_type")
    private String contentType;

    @ManyToOne @JoinColumn(name = "article_no") @JsonIgnore
    private Notice notice;
    @ManyToOne @JoinColumn(name = "subject_id") @JsonIgnore
    private Subject subject;

}
