package com.pickle.web.schedules;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @Column(name = "period", nullable = true)
    private int period;

    @Column(name = "mon", nullable = true)
    private String mon;

    @Column(name = "tue", nullable = true)
    private String tue;

    @Column(name = "wed", nullable = true)
    private String wed;

    @Column(name = "thu", nullable = true)
    private String thu;

    @Column(name = "fri", nullable = true)
    private String fri;

    @Column(name = "checker", nullable = false)
    private int checker;

}