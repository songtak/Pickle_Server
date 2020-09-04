package com.pickle.web.chatbot;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component @Lazy
@Data @Alias("exam")
@MappedTypes(LocalDate.class)
public class Exam {
    private int id;
    private int examKind, examNum;
    private String examQuestion, examDivision, examChoice1, subjectCode, examChoice2, examChoice3, examChoice4, examChoice5, examAnswer, examContent;
    private LocalDateTime insertDate;
}