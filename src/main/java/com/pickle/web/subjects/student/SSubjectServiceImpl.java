package com.pickle.web.subjects.student;


import com.pickle.web.subjects.SubjectDetail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


interface ISSubjectService{

    List<SubjectDetail> getSubjectDetail(String subjectCode);
    List<SubjectDetail> findSubInfo(String subjectCode);
}



@Service
@AllArgsConstructor
public class SSubjectServiceImpl implements ISSubjectService{
    private final SSubjectRepository sSubjectRepository;

    @Override
    public List<SubjectDetail> getSubjectDetail(String subjectCode) {
        String subject = subjectCode.substring(0,4);
        return sSubjectRepository.findSubjectDetail(subject);
    }

    @Override
    public List<SubjectDetail> findSubInfo(String subjectCode) {
        return sSubjectRepository.findGetSubInfo(subjectCode);
    }

}
