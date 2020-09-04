package com.pickle.web.attendances.teacher;

import com.pickle.web.attendances.Attendance;
import lombok.AllArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController @AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tattendance")
public class TAttendanceController {
    private final TAttendanceService aService;

    @GetMapping("/all")
    public List<Attendance> findAll() {
        return (List<Attendance>) aService.findAll();
    }

    //Confirmed Working Properly
    //@RequestBody {clickCreate, clickStreaming, userCode}
    @Transactional
    @GetMapping("/checkPresent")
    public void update(int clickCreate, int clickStreaming, int userCode) {
        int checkPresent = clickCreate ^ clickStreaming;
        LocalDate localDate = LocalDate.now();
        aService.update(userCode, localDate, checkPresent);
    }
    //Confirmed working properly
    //@RequestBody {studentUserCode, localdate}
    @Transactional
    @GetMapping("/checkTardy")
    public void updateToTardy(int userCode, LocalDate localDate){
        aService.updateTardy(userCode,localDate);
    }

    //Confirmed working properly
    @GetMapping("/singleday/{grade}/{ban}")
    public List<Integer> getSingleDay(@PathVariable(value="grade")int curGrade,
                                      @PathVariable(value="ban") int homeClass,
                                      @RequestParam String javaDate) {
        LocalDate localDate = LocalDate.now();
        LocalDate cDate = LocalDate.of(Integer.parseInt(javaDate.split("-")[0])
                ,Integer.parseInt(javaDate.split("-")[1])
                ,Integer.parseInt(javaDate.split("-")[2]));

        if(cDate.equals(localDate)){
            localDate = localDate.minus(1, ChronoUnit.DAYS);
            return aService.getSingleDay(localDate, curGrade, homeClass);
        }else{
            return aService.getSingleDay(cDate, curGrade, homeClass);}
    }

    //Query working but reversely added to list
    @GetMapping("/weeklychart/{curGrade}/{homeClass}")
    public List<List<Integer>> getWeeklyChart(@PathVariable(value="curGrade")int curGrade,
                                              @PathVariable(value="homeClass")int homeClass,
                                              @RequestParam String javaDate) {

        LocalDate localDate = LocalDate.now();
        LocalDate cDate = LocalDate.of(Integer.parseInt(javaDate.split("-")[0])
                ,Integer.parseInt(javaDate.split("-")[1])
                ,Integer.parseInt(javaDate.split("-")[2]));

        if(cDate.equals(localDate)){
            localDate = localDate.minus(1, ChronoUnit.DAYS);
        }else{
            localDate = cDate;}
        List<List<Integer>> list = new ArrayList<>();
        int day = localDate.getDayOfWeek().getValue();

        if (day > 5) day = 5;
        else day = day;

        for (int i = 0; i < day; i++) {
            list.add(aService.getSingleDay(localDate.minus(i, ChronoUnit.DAYS),curGrade,homeClass));
        }
        return list;
    }

    //Confirmed working properly(Server/ Client)
    @GetMapping("/detaillist")
    public List<AttendanceList> getList(@RequestParam String cCurGrade,
                                        @RequestParam String cHomeClass,
                                        @RequestParam String year) {
        int curGrade = Integer.parseInt(cCurGrade);
        int homeClass = Integer.parseInt(cHomeClass);
        int selectedYear = Integer.parseInt(year);
        return aService.getDetailList(curGrade, homeClass,selectedYear);
    }

    //search (necessary?)
    @GetMapping("/searchstudent")
    public AttendanceList getStudent(@RequestParam String searchWord) {
        return null;
    }
}