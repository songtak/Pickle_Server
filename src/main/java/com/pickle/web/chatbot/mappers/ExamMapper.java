package com.pickle.web.chatbot.mappers;

import com.pickle.web.chatbot.Exam;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public interface ExamMapper {
    public ArrayList<Exam> selectList(Exam exam);
}
