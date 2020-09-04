package com.pickle.web.attendances.student;

import com.pickle.web.commons.Box;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/sattendance")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SAttendanceController {
    private final ISAttendanceRepository isAttendanceRepository;
    private final ISAttendanceService iSAttendanceService;
    private final Box box;

    public SAttendanceController(@Qualifier("SAttendanceRepository") ISAttendanceRepository isAttendanceRepository, ISAttendanceService iSAttendanceService, Box box) {
        this.isAttendanceRepository = isAttendanceRepository;
        this.iSAttendanceService = iSAttendanceService;
        this.box = box;
    }


    @GetMapping("/{userCode}")
    public HashMap<?,?> getUserAtte(@PathVariable int userCode){
        box.clear();
        box.put("sAtte",iSAttendanceService.findSAtte(userCode));       //개인 출석 현황 출석, 지각, 결석
        box.put("sAtteSum",iSAttendanceService.findAtteSum(userCode));
        System.out.println(iSAttendanceService.findAtteSum(userCode));
        //System.out.println(iSAttendanceService.findAtteSum(userCode));
        return box.get();
    }

}
