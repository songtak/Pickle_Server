package com.pickle.web.notices;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.web.file.File;
import com.pickle.web.users.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Getter@Setter
@NoArgsConstructor
@Table(name = "notice", uniqueConstraints = {@UniqueConstraint(columnNames = {"article_no"})})
@Component
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_no")
    private Long id;
    @Column(name = "category")
    private String category;
    @Column(name = "title")
    private String title;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;
    @Column(name = "contents")
    private String contents;
    @CreationTimestamp @Column(name = "create_date")
    private LocalDateTime createDate;

    @ManyToOne @JoinColumn(name = "id") @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL) @JsonIgnore
    private List<File> files;
    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL) @JsonIgnore
    private List<NoticeComment> noticeComment;
}