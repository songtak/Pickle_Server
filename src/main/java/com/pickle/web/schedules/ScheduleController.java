package com.pickle.web.schedules;

import com.pickle.web.commons.Box;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController @AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ScheduleController {
    public final ScheduleService sService;
    public final Box box;
    public Long findId(@RequestBody Schedule item){
        return sService.findId(item);
    }

    //Confirmed teacher,student working properly
    @GetMapping("/tschedule/read/{userCode}")
    public Map<String,TimetableVO> getTimetable(@PathVariable String userCode) {
        Map<String, TimetableVO> map = new HashMap<>();
        int id = Integer.parseInt(userCode);
        if (userCode.substring(4, 6).equals("00")){
            map.put("tTimetable",sService.getTTimetable(id));
        } else {
            map.put("sTimetable",sService.getTimetable(id));
        }
        return map;
    }


    //student class
    @GetMapping("/schedule/student/{userCode}")
    public HashMap<String, ?> sTimetable(@PathVariable int userCode ) {
        Box<List<?>> box = new Box<>();
        box.clear();
        box.put("student", sService.getSUserTimetable(userCode));
        return box.get();
    }

    @PostMapping("/tschedule/updateSchedule/{cUserCode}")
    @Transactional
    public void updateTable(@RequestBody Map<String,TimetableVO> payload,
                            @PathVariable String cUserCode){
        int userCode = Integer.parseInt(cUserCode);
        TimetableVO timetable = payload.get("payload");
        sService.updateTable(userCode,timetable);
    }


    @GetMapping("/tschedule/setTime/{cUserCode}")
    public String setTime(@PathVariable String cUserCode) {
        TimetableVO timetable = getTimetable(cUserCode).get("tTimetable");
        String returnTime = "";
        String localDate = LocalDate.now().toString();
        int classTime = 0;
        for (int i = 0; i < timetable.getMon().size(); i++) {
            if (!timetable.getMon().get(i).equals("-")) {
                classTime = i + 1;
                switch (classTime) {
                    case 1: returnTime = " 09:00:00";
                        break;
                    case 2: returnTime = " 10:00:00";
                        break;
                    case 3: returnTime = " 11:00:00";
                        break;
                    case 4: returnTime = " 13:00:00";
                        break;
                    case 5: returnTime = " 14:00:00";
                        break;
                    case 6: returnTime = " 15:00:00";
                        break;
                    default: returnTime = " 00:00:00";
                        break;
                }
            }
        }
        return  " 14:00:00";
    }

    @GetMapping("/tschedule/dndTimetable/{userCode}")
    @Transactional
    public Map<String,DndTimetableVO> dndtest(@PathVariable String userCode){
        Map<String,DndTimetableVO> map = new HashMap<>();
        int id = Integer.parseInt(userCode);
        DndTimetableVO dndTimetableVO = sService.getDndTimetable(id);
        map.put("list",dndTimetableVO);
        return  map;
    }
    @PostMapping("/tschedule/deleteOne/{userCode}")
    @Transactional
    public void deleteOne(@RequestBody DndTimetableEl payload,
                          @PathVariable String userCode){
        int id = Integer.parseInt(userCode);
       sService.updateOne(payload, id);
    }
    @GetMapping("/tschedule/timer/{payloadId}/{payloadSubCode}")
    public Map<String,Long> test(@PathVariable String payloadId,
                                   @PathVariable String payloadSubCode){
        Map<String, Long> map = new HashMap<>();
        LocalDate localDate = LocalDate.now();
        LocalDate pickedDate = LocalDate.now();
        if(!payloadSubCode.equals("-")) {
            int today = localDate.getDayOfWeek().getValue();
            int intPickedDay = localDate.getDayOfWeek().getValue();

            switch (payloadId.substring(0, 3)) {
                case "mon":
                    intPickedDay = 1;
                    break;
                case "tue":
                    intPickedDay = 2;
                    break;
                case "wed":
                    intPickedDay = 3;
                    break;
                case "thu":
                    intPickedDay = 4;
                    break;
                case "fri":
                    intPickedDay = 5;
                    break;
                default:
                    intPickedDay = 0;
            }
            String pickedTime = "";
            switch (payloadId.substring(3)) {
                case "0":
                    pickedTime = " 09:00:00";
                    break;
                case "1":
                    pickedTime = " 10:00:00";
                    break;
                case "2":
                    pickedTime = " 11:00:00";
                    break;
                case "3":
                    pickedTime = " 12:00:00";
                    break;
                case "4":
                    pickedTime = " 14:00:00";
                    break;
                case "5":
                    pickedTime = " 15:00:00";
                    break;
                case "6":
                    pickedTime = " 16:00:00";
                    break;
            }
            int difference = today - intPickedDay;
            if (difference < 0) {
                String returnDate = pickedDate.plus(-difference,ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String returnTimer = returnDate+pickedTime;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime localReturnTimer = LocalDateTime.parse(returnTimer,formatter);
                Instant instant = localReturnTimer.atZone(ZoneId.systemDefault()).toInstant();
                long milliseconds = instant.toEpochMilli();
                map.put("time", milliseconds);
            } else if (difference > 0){
                String returnDate = pickedDate.plus(7-difference,ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String returnTimer = returnDate + pickedTime;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime localReturnTimer = LocalDateTime.parse(returnTimer,formatter);
                Instant instant = localReturnTimer.atZone(ZoneId.systemDefault()).toInstant();
                long milliseconds = instant.toEpochMilli();
                map.put("time", milliseconds);
            }
            else{
                LocalDateTime currentDateTime = LocalDateTime.now();
                String stringPickedTime = localDate.toString() + pickedTime.substring(0,9);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime modifiedPickedTime = LocalDateTime.parse(stringPickedTime,formatter);
                if(currentDateTime.isBefore(modifiedPickedTime)){
                    String returnDate = localDate.toString();
                    String returnTimer = returnDate + pickedTime;
                    LocalDateTime localReturnTimer = LocalDateTime.parse(returnTimer,formatter);
                    Instant instant = localReturnTimer.atZone(ZoneId.systemDefault()).toInstant();
                    long milliseconds = instant.toEpochMilli();
                    map.put("time", milliseconds);
                } else{
                    String returnDate = localDate.plus(7,ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String returnTimer = returnDate + pickedTime;
                    LocalDateTime localReturnTimer = LocalDateTime.parse(returnTimer,formatter);
                    Instant instant = localReturnTimer.atZone(ZoneId.systemDefault()).toInstant();
                    long milliseconds = instant.toEpochMilli();
                    map.put("time", milliseconds);
                }
            }
        }
        //if subject is null
        else {
            LocalDateTime currentDateTime = LocalDateTime.now();
            map.put("time", (long) 0);
        }
        return map;
    }
}