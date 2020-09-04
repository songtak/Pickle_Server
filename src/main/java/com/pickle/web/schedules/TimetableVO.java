package com.pickle.web.schedules;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TimetableVO {
    private List<String> mon;
    private List<String> tue;
    private List<String> wed;
    private List<String> thu;
    private List<String> fri;

}
