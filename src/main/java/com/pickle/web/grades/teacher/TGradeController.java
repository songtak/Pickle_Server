package com.pickle.web.grades.teacher;

import com.pickle.web.commons.Proxy;
import com.pickle.web.grades.GradeVO;
import com.pickle.web.grades.Mock;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping("/teacher/grade")
@AllArgsConstructor
public class TGradeController {
    private final TGradeService service;
    private final TGradeRepository repository;
    @Autowired Proxy proxy;
    @GetMapping("/input/homeClass/{schoolCode}/{grade}")
    public List<String> getClass(@PathVariable("schoolCode")int schoolCode, @PathVariable("grade") int grade){
        List<Integer> list = repository.getClassBySchoolCode(schoolCode, grade);
        List<String> result = new ArrayList<>();
        for(Integer i : list){
            if(i/10 == 0) {
                result.add(0+String.valueOf(i));
            }else {
                result.add(String.valueOf(i));
            }
        }
        return result;
    }
    @GetMapping("/input/student/{schoolCode}/{grade}/{homeClass}")
    public List<String> getStudent(@PathVariable("schoolCode")int schoolCode, @PathVariable("grade") int grade, @PathVariable("homeClass") int homeClass){
        return repository.getStudentBySchoolCodeAndGrade(schoolCode, grade, homeClass);
    }
    @GetMapping("/get/{userInfo}")
    public List<HashMap> getScore(@PathVariable String userInfo){
        return service.getScoreByUser(userInfo);
    }
    @PostMapping("/input")
    public void insert(@RequestBody HashMap<?, ?> map){ service.insert(map); }
    @GetMapping("/class/{schoolCode}/{grade}/{homeClass}")
    public HashMap<String, List<HashMap>> getClassScore(@PathVariable("schoolCode")int schoolCode, @PathVariable("grade") int grade, @PathVariable("homeClass") int homeClass){
        HashMap<String, List<HashMap>> map = new HashMap<>();
        map.put("barChartClass", service.getClassGrade(schoolCode, grade, homeClass));
        map.put("barChartTotal", service.getTotalGrade(schoolCode, grade));
        HashMap<String, List<HashMap>> rank = service.getScatter(schoolCode, grade, homeClass);
        map.put("scatterChartClass", rank.get("classResult"));
        map.put("scatterChartTotal", rank.get("totalResult"));
        HashMap<String, List> classRank = service.getClassRank();
        map.put("totalRank", classRank.get("totalRank"));
        map.put("subRank", classRank.get("subRank"));
        return map;
    }
    @GetMapping("/one/{userInfo}")
    public HashMap<String, List<HashMap>> getOneGrade(@PathVariable String userInfo){
        HashMap<String, List<HashMap>> map = new HashMap<>();
        //한 학생의 전 학년동안의 성적
        List<GradeVO> list = proxy.convertToGradeVo(repository.getTotalGradeByUser(service.findUserByUserInfoWithSchoolCode(userInfo)));
        map.put("barChart", service.getGradeBySemester(list));
        map.put("lineChart", service.getLineChart(list));
        map.put("radarChart", service.getRadarChart(list));
        return map;
    }
    @GetMapping("/make")
    public void makeScores(){
        service.makeScores();
    }
    @GetMapping("/mock/get/{userInfo}")
    public List<Mock> getMock(@PathVariable String userInfo){
        return service.getMockByUser(userInfo);
    }
}
