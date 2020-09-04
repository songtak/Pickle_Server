package com.pickle.web.grades.student;


import com.pickle.web.commons.Box;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/student/grade")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SGradeController {
    private final ISGradeService isGradeService;
    private final Box box;

    public SGradeController( ISGradeService isGradeService, Box box){
        this.isGradeService = isGradeService;
        this.box = box;
    }

    @GetMapping("/list")
    public HashMap<?,?> getStudentGrade(){
        box.clear();
        box.put("studentGrade",isGradeService.getStudentGrade());
        return box.get();
    }


    @GetMapping("/{userCode}")
    public HashMap<?,?> getMainChart(@PathVariable int userCode){
        box.clear();
        box.put("sgmainChart",isGradeService.getMainChart(userCode));
        box.put("sguserScore",isGradeService.getUserScore(userCode));

        box.put("kor",isGradeService.getKorGrade(userCode));

        box.put("eng",isGradeService.getEngGrade(userCode));

        box.put("mat",isGradeService.getMatGrade(userCode));

        box.put("his",isGradeService.getHisGrade(userCode));

        box.put("phl",isGradeService.getPhlGrade(userCode));

        box.put("eco",isGradeService.getEcoGrade(userCode));

        box.put("phy",isGradeService.getPhyGrade(userCode));

        box.put("bio",isGradeService.getBioGrade(userCode));

        box.put("for2",isGradeService.getForGrade(userCode));

        return box.get();
    }


    @PostMapping("/search/{userCode}")
    public HashMap<?,?> getSearchGrade(@PathVariable int userCode, @RequestBody SGradeOptions searchGradeValue){

        if(searchGradeValue.getExamList().length>0){
            box.clear();
            HashMap<String,int[]> result = isGradeService.searchUserScore(userCode,searchGradeValue);
            HashMap<String,int[]> totalAvg = isGradeService.searchTotalScore(searchGradeValue);

            box.put("sgSearchUser",result);
            box.put("sgSearchTotal",totalAvg);
        }
        return box.get();
    }




}
