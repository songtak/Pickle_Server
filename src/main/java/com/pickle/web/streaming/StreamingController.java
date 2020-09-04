package com.pickle.web.streaming;

import com.pickle.web.commons.Box;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("/streamings")
public class StreamingController {
    private final StreamingService streamingService;
    private final Box box;

    @GetMapping("/student/{userCode}")
    public Map<?,?> findClassCode(@PathVariable String  userCode){
        box.clear();
        box.put("classCode", streamingService.findSClassCode(Integer.parseInt(userCode)));
        box.put("studentList",streamingService.findSStudentList(Integer.parseInt(userCode)));
        return box.get();
    }

    @GetMapping("/teacher/{userCode}")
    public Map<?,?> findStudentList(@PathVariable String userCode){
        box.clear();
        String classCode = streamingService.findTClassCode(Integer.parseInt(userCode));
        if (!classCode.equals("")){
            box.put("classCode",classCode);
            box.put("studentList", streamingService.findTStudentList(classCode));;
        }else{
            box.put("classCode","refuse");
            box.put("studentList", "refuse");
        }
        return box.get();
    }

}
