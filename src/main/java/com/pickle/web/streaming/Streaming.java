package com.pickle.web.streaming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Lazy;

@Lazy
@ToString
@Getter
@Setter
@AllArgsConstructor
public class Streaming {
private String mon, tue, wed, thu, fri;
}
