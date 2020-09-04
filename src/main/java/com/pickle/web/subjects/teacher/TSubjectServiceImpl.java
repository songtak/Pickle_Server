package com.pickle.web.subjects.teacher;

import com.pickle.web.commons.JpaService;
import com.pickle.web.subjects.SubjectDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

interface TSubjectService extends JpaService<SubjectDetail> {
    List<SubjectDetail> getDetailList(int userCode);
    void updateOne2(Map<String,String> contents, int detailId);
    void deleteOne(int userCode, int lessonId);

    void updateAll(int userCode, List<Map<String,String>> details);

    Map<String,String> getBasicInfo(int userCode);
}
@Service
@RequiredArgsConstructor
public class TSubjectServiceImpl implements TSubjectService {
    private final TSubjectRepository sRepository;

    @Override
    public Optional<SubjectDetail> findById(String id) {
        return sRepository.findById(Long.valueOf(id));
    }
    @Override public Iterable<SubjectDetail> findAll() {
        return sRepository.findAll();
    }
    @Override public int count() {
        return (int) sRepository.count();
    }
    @Override public void delete(String id) { }
    @Override public boolean exists(String id) {return sRepository.existsById(Long.valueOf(id));}

    @Override
    public List<SubjectDetail> getDetailList(int userCode) {
        return sRepository.getDetailList(userCode);
    }

    @Override
    public void updateOne2(Map<String,String> contents, int detailId) {
        SubjectDetailVO subjectDetailVO = new SubjectDetailVO();
        subjectDetailVO.setLessonNo(Integer.parseInt(contents.get("lessonNo")));
        subjectDetailVO.setLessonTitle(contents.get("lessonTitle"));
        subjectDetailVO.setLessonDetail(contents.get("lessonDetail"));
        sRepository.updateOne2(subjectDetailVO, detailId);
    }

    @Override
    public void deleteOne(int userCode, int lessonId) {
        sRepository.deleteOne(userCode, lessonId);
    }

    @Override
    public void updateAll(int userCode, List<Map<String,String>> details) {
        for(int i = 0 ; i < details.size(); i++) {
            details.get(i).put("lessonNo", String.valueOf(i+1));
            sRepository.updateList(details.get(i));
        }
    }

    @Override
    public Map<String,String> getBasicInfo(int userCode) {
        Map<String,String> names = new HashMap<>();
        names.put("subjectName",sRepository.getSubjectNameByUserCode(userCode));
        names.put("userName",sRepository.getUserNameByUserCode(userCode));
        names.put("subjectCode",sRepository.getSubjectCodeByUserCode(userCode));
        return names;
    }

}
