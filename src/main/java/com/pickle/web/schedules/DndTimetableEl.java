package com.pickle.web.schedules;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DndTimetableEl {
    private String id,day,subjectCode,subjectName;
    int period;
}
