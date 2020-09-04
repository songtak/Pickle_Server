package com.pickle.web.main;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping("/main")
@AllArgsConstructor
public class MainController {
    private final MainService mainService;
    @GetMapping("/teacher/grade/{schoolCode}/{curGrade}/{homeClass}")
    public List<HashMap> getClassGrade(@PathVariable("schoolCode") int schoolCode, @PathVariable("curGrade") int curGrade, @PathVariable("homeClass") int homeClass){
        return mainService.getClassGrade(schoolCode, curGrade, homeClass);
    }
    @GetMapping("/student/grade/{id}")
    public List<HashMap> getStudentGrade(@PathVariable String id){
        return mainService.getStudentGrade(id);
    }
    @GetMapping("/teacher/attendance/{schoolCode}/{curGrade}/{homeClass}")
    public HashMap<String, Integer> getClassAttendance(@PathVariable("schoolCode") int schoolCode, @PathVariable("curGrade") int curGrade, @PathVariable("homeClass") int homeClass){
        return mainService.getClassAttendance(schoolCode, curGrade, homeClass);
    }
    @GetMapping("/student/attendance/{id}")
    public HashMap<String, Integer> getStudentAttendance(@PathVariable String id){
        return mainService.getStudentAttendance(id);
    }
}
