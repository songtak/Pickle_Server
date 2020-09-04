package com.pickle.web.notices;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pickle.web.users.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@Component
@Table(name = "notice_comment", uniqueConstraints = {@UniqueConstraint(columnNames = {"comment_no"})})
public class NoticeComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private Long id;
    @Column(name = "comment_contents")
    private String commentContents;

    @ManyToOne @JoinColumn(name = "id") @JsonIgnore
    private User user;
    @ManyToOne @JoinColumn(name = "article_no") @JsonIgnore
    private Notice notice;
}