package com.pickle.web.main;

import com.pickle.web.attendances.student.SAttendanceRepository;
import com.pickle.web.commons.Proxy;
import com.pickle.web.grades.GradeVO;
import com.pickle.web.grades.teacher.TGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
interface MainService{
    List<HashMap> getClassGrade(int schoolCode, int curGrade, int homeClass);
    List<HashMap> getStudentGrade(String id);
    HashMap<String, Integer> getClassAttendance(int schoolCode, int curGrade, int homeClass);
    HashMap<String, Integer> getStudentAttendance(String id);
}
@Service
public class MainServiceImpl implements MainService{
    private final TGradeRepository tGradeRepository;
    private final SAttendanceRepository sAttendanceRepository;
    @Autowired Proxy proxy;
    public MainServiceImpl(TGradeRepository tGradeRepository, SAttendanceRepository sAttendanceRepository) {
        this.tGradeRepository = tGradeRepository;
        this.sAttendanceRepository = sAttendanceRepository;
    }

    public List<String> getSubjectNames(List<GradeVO> list){
        List<String> subjectNames = new ArrayList<>();
        for(GradeVO i : list){
            if(!subjectNames.contains(i.getSubjectName())) {
                subjectNames.add(i.getSubjectName());
            }
        }
        return subjectNames;
    }

    @Override @Transactional
    public List<HashMap> getClassGrade(int schoolCode, int curGrade, int homeClass) {
        List<GradeVO> result = proxy.convertToGradeVo(tGradeRepository.getClassGrade(schoolCode, curGrade, homeClass));
        String[] subNames = {"국어", "영어", "수학", "경제", "생명과학", "제2외국어"};
        int memberCount = tGradeRepository.getClassMemberCount(schoolCode, curGrade, homeClass);
        List<HashMap> maplist = new ArrayList<>();
        for(int i=0; i< subNames.length; i++){
            int score = 0;
            for(GradeVO j : result){
                if(j.getSubjectName().startsWith(subNames[i])) score += j.getScore();
            }
            score = score / memberCount / tGradeRepository.getSemesterCount();
            HashMap<String, String> map = new HashMap<>();
            map.put("sub", subNames[i]);
            map.put("score", String.valueOf(score));
            maplist.add(map);
        }
        return maplist;
    }

    @Override @Transactional
    public List<HashMap> getStudentGrade(String id) {
        List<GradeVO> result = proxy.convertToGradeVo(tGradeRepository.getStudentGrade(Long.parseLong(id)));
        String[] subNames = {"국어", "영어", "수학", "경제", "생명과학", "제2외국어"};
        List<HashMap> maplist = new ArrayList<>();
        for(int i=0; i< subNames.length; i++){
            int score = 0;
            for(GradeVO j : result){
                if(j.getSubjectName().startsWith(subNames[i])) score += j.getScore();
            }
            score = (score) / tGradeRepository.getSemesterCount();
            HashMap<String, String> map = new HashMap<>();
            map.put("sub", subNames[i]);
            map.put("score", String.valueOf(score));
            maplist.add(map);
        }
        return maplist;
    }

    @Override @Transactional
    public HashMap<String, Integer> getClassAttendance(int schoolCode, int curGrade, int homeClass) {
        HashMap<String, Integer> result = new HashMap<>();
        int totalMember = tGradeRepository.getUserCountByGrade(schoolCode, curGrade);
        int absent = sAttendanceRepository.getTodayClassAbsent(curGrade, homeClass);
        result.put("count", totalMember);
        result.put("percent", ((totalMember-absent)*100/totalMember));
        return result;
    }

    @Override
    public HashMap<String, Integer> getStudentAttendance(String id) {
        HashMap<String, Integer> result = new HashMap<>();
        result.put("presentCount", sAttendanceRepository.getPresentCountById(Long.parseLong(id)));
        result.put("totalDay", sAttendanceRepository.getTotalDayById(Long.parseLong(id)));
        return result;
    }
}
