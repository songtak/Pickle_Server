package com.pickle.web.grades;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pickle.web.users.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name= "mock")
public class Mock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mock_id", nullable = false)
    private Long id;
    @Column(name = "semester_code", nullable = false)
    private int semester_code;
    @Column(name = "score", nullable = false)
    private int score;
    @Column(name = "standard", nullable = false)
    private int standard;
    @Column(name = "percent", nullable = false)
    private int percent;
    @Column(name = "subject", nullable = false)
    private String subject;

    @ManyToOne @JoinColumn(name = "user_code", referencedColumnName = "user_code") @JsonIgnore
    private User user;
}
