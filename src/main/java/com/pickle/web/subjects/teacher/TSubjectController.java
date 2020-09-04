package com.pickle.web.subjects.teacher;

import com.pickle.web.subjects.SubjectDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController @RequiredArgsConstructor
@RequestMapping("/tsubject")
public class TSubjectController {
    private final TSubjectService sService;

    //instructor name and subject name
    @GetMapping("/basicInfo")
    public Map<String,Map<String,String>>  getBasicInfo(@RequestParam String cUserCode){
        int userCode = Integer.parseInt(cUserCode);
        Map<String,Map<String,String>> map = new HashMap<>();
        map.put("map", sService.getBasicInfo(userCode));
        return map;
    }

    //Confirmed
    @GetMapping("/detailList/{cUserCode}")
    @Transactional
    public Map<String,List<SubjectDetail>> getDetailList(@PathVariable String cUserCode){
        int userCode = Integer.parseInt(cUserCode);
        Map<String,List<SubjectDetail>> map = new HashMap<>();
        map.put("list",sService.getDetailList(userCode));
        return map;
    }

    //save == update
    //Confirmed test needed
    //@RequestBody int lesson, int userCode, SubjectDetail updatedDetail
    @PostMapping("/updateList/{cUserCode}")
    @Transactional
    public void updateList(@RequestBody Map<String,List<Map<String,String>>> updatedList,
                           @PathVariable String cUserCode){
        List<Map<String,String>> updated = updatedList.get("updatedList");
        int userCode = Integer.parseInt(cUserCode);
        sService.updateAll(userCode,updated);
    }

//    @RequestParam(value = "userCode") int userCode,
//    @RequestParam(value = "lessnonNo", required = false)int lessonNo
//    @RequestParam(value = "content") List<String> contents : usercode lessonNo, title, detail
//    @RequestBody Map<String,?> content
    @PostMapping("/updateOne")
    @Transactional
    public void update(@RequestBody Map<String,String> payload){
        int detailId = Integer.parseInt(payload.get("lessonId"));
        sService.updateOne2(payload, detailId);
    }

    @GetMapping("/deleteOne/{userCode}")
    @Transactional
    public void deleteOne(@PathVariable String userCode,
                          @RequestParam String lessonId){
        int id = Integer.parseInt(userCode);
        int dlessonId = Integer.parseInt(lessonId);
        sService.deleteOne(id, dlessonId);
    }
    @PostMapping("/deleteAll/{userCode}")
    @Transactional
    public void deleteAll(@RequestBody Map<String,List<Integer>> lessonIds,
                          @PathVariable String userCode){
        int id = Integer.parseInt(userCode);
        List<Integer> deleteList = lessonIds.get("list");
        deleteList.forEach(dlessonId ->{
            sService.deleteOne(id,dlessonId);
        });
    }
}
