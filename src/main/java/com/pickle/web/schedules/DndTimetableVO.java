package com.pickle.web.schedules;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class DndTimetableVO {
    private List<DndTimetableEl> dndMon,dndTue,dndWed,dndThu,dndFri;

}

