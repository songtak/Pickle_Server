package com.pickle.web.commons;

import com.pickle.web.grades.Grade;
import com.pickle.web.grades.GradeVO;
import com.pickle.web.grades.teacher.TGradeRepository;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Proxy {
    private final TGradeRepository repository;
    private int tc1, tc2, tc3;
    private int[][] classes = new int[3][15];
    private int semesterCount;

    public Proxy(TGradeRepository repository) {
        this.repository = repository;
    }

    public void countAll(int schoolCode){
        for(int i = 0; i < classes.length; i++ ){
            for(int j=0; j< classes[i].length; j++){
                classes[i][j] = repository.getClassMemberCount(schoolCode, i+1, j+1);
            }
        }
        this.tc1 = repository.getUserCountByGrade(schoolCode, 1);
        this.tc2 = repository.getUserCountByGrade(schoolCode, 2);
        this.tc3 = repository.getUserCountByGrade(schoolCode, 3);
        this.semesterCount = repository.getSemesterCount();
    }

    public int count(int curGrade, int homeClass){
        int result = 0;
        if(homeClass == 0){
            switch (curGrade){
                case 1: result = tc1; break;
                case 2: result = tc2; break;
                case 3: result = tc3; break;
            }
        }else {
            result = classes[curGrade-1][homeClass-1];
        }
        return result;
    }
    public int getSemesterCount(){
        return this.semesterCount;
    }

    public List<GradeVO> convertToGradeVo(List<Grade> list) {
        List<GradeVO> convert = new ArrayList<>();
        GradeVO vo;
        for(Grade i : list){
            vo = new GradeVO();
            vo.setGradeId(i.getId());
            vo.setScore(i.getScore());
            vo.setSubjectId(i.getSubject().getId());
            vo.setSemesterCode(i.getSemesterCode());
            vo.setSubjectName(i.getSubject().getSubjectName());
            vo.setUserCode(i.getUser().getUserCode());
            vo.setName(i.getUser().getName());
            convert.add(vo);
        }
        return convert;
    }
}
