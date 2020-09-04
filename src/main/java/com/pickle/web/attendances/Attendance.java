package com.pickle.web.attendances;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pickle.web.users.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Getter @Setter @ToString
@NoArgsConstructor
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private Long id;

    @CreationTimestamp
    @Column(name = "date", unique = true)
    private LocalDate date;

    @Column(name = "present", nullable = true)
    private int present;

    @Column(name = "absent", nullable = true)
    private int absent;

    @Column(name = "tardy", nullable = true)
    private int tardy;

    @ManyToOne @JsonIgnore
    @JoinColumn(name = "user_code", referencedColumnName = "user_code")
    private User user;


}