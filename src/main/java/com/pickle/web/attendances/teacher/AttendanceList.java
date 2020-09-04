package com.pickle.web.attendances.teacher;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AttendanceList {
    private int userCode;
    private String name;
    private List<Integer> months;
}
