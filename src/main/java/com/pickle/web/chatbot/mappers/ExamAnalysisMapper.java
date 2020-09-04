package com.pickle.web.chatbot.mappers;

import com.pickle.web.chatbot.ExamAnalysis;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public interface ExamAnalysisMapper {
    public ArrayList<ExamAnalysis> selectList(ExamAnalysis examAnalysis);
    public int codeExamKind(String examAnalysis);
    public String codeSubjectKind(String examAnalysis);
}
