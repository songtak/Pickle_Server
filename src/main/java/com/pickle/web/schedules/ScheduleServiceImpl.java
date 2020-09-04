package com.pickle.web.schedules;

import com.pickle.web.commons.Box;
import com.pickle.web.commons.JpaService;
import com.pickle.web.subjects.teacher.TSubjectRepository;
import com.pickle.web.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

interface ScheduleService extends JpaService<Schedule> {
    TimetableVO getTTimetable(int userCode);
    TimetableVO getTimetable(int userCode);
    void updateTable(int userCode, TimetableVO timetable);
    void update(Box<List<Schedule>> schedule);
    List<Schedule> getSTimetable(int userCode);
    List<Object> getSUserTimetable(int userCode);
    Long findId(Schedule item);
    DndTimetableVO getDndTimetable(int userCode);
    void updateOne(DndTimetableEl payload, int userCode);
}
@RequiredArgsConstructor
@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final TSubjectRepository tSubjectRepository;
    int division = 0;
    @Override public Optional<Schedule> findById(String id) {
        return scheduleRepository.findById(Long.valueOf(id));
    }
    @Override public Iterable<Schedule> findAll() {
        return scheduleRepository.findAll();
    }
    @Override public int count() {
        return (int) scheduleRepository.count();
    }
    @Override public void delete(String id) {
        scheduleRepository.deleteById(Long.valueOf(id));
    }
    @Override public boolean exists(String id) {
        return scheduleRepository.existsById(Long.valueOf(id));
    }
    @Override public void update(Box<List<Schedule>> schedule){}

    //Confirmed working properly
    @Override
    public TimetableVO getTimetable(int userCode) {
        List<Schedule> list = new ArrayList<>();

        list = getChecker(userCode, list);
        TimetableVO timetable = new TimetableVO();
        List<String> mon = new ArrayList<>();
        List<String> tue = new ArrayList<>();
        List<String> wed = new ArrayList<>();
        List<String> thu = new ArrayList<>();
        List<String> fri = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            mon.add(i, list.get(i).getMon());
        }
        for (int i = 0; i < list.size(); i++) {
            tue.add(i, list.get(i).getTue());
        }
        for (int i = 0; i < list.size(); i++) {
            wed.add(i, list.get(i).getWed());
        }
        for (int i = 0; i < list.size(); i++) {
            thu.add(i, list.get(i).getThu());
        }
        for (int i = 0; i < list.size(); i++) {
            fri.add(i, list.get(i).getFri());
        }
        timetable.setMon(mon);
        timetable.setTue(tue);
        timetable.setWed(wed);
        timetable.setThu(thu);
        timetable.setFri(fri);
        return timetable;
    }

    @Override
    public void updateTable(int userCode, TimetableVO timetable) {
        TimetableVO original = getTTimetable(userCode);

    }

    public List<Schedule> getChecker(int userCode, List<Schedule> list) {
        int checker = 0;
        if (userRepository.findByUserCode(userCode).getPositionChecker() == 1) {
            if (userRepository.findByUserCode(userCode).getHomeClass() < 9) division = 1;
            else division = 2;
            checker = Integer.parseInt(String.valueOf(userRepository.findByUserCode(userCode).getCurGrade()) + String.valueOf(division));
            list = scheduleRepository.studentTimetable(checker);
        } else {
            String subCode = tSubjectRepository.findSubjectIdByUserCode(userCode);
            switch (subCode.substring(0, 3)) {
                case "kor": case "eng": case "mat": case "his": case "for":
                    if (userRepository.findByUserCode(userCode).getHomeClass() < 9) division = 1;
                    else division = 2;
                    checker = Integer.parseInt(String.valueOf(userRepository.findByUserCode(userCode).getCurGrade()) + String.valueOf(division));
                    list = scheduleRepository.studentTimetable(checker);
                    break;
                case "phl": case "eco": case "bio": case "phy":
                    int curGrade = userRepository.findByUserCode(userCode).getCurGrade();
                    list = scheduleRepository.teacherTimetable(curGrade);
            }
        }
        return list;
    }

    //Confirmed working properly
    @Override
    public TimetableVO getTTimetable(int userCode) {
        TimetableVO tableMask = new TimetableVO();
        TimetableVO timetable = getTimetable(userCode);
        String subCode = tSubjectRepository.findSubjectIdByUserCode(userCode);
        String subName = tSubjectRepository.getSubjectNameByUserCode(userCode);
        String subjectName = subName.substring(2)+"학년 " + subName.substring(0,2);

        List<String> monFilter = new ArrayList<>();
        for (int i = 0; i < timetable.getMon().size(); i++) {
            String a = (timetable.getMon().get(i).substring(0, 4).equals(subCode)) ? subjectName : "-";
            monFilter.add(i, a);
        }
        List<String> tueFilter = new ArrayList<>();
        for (int i = 0; i < timetable.getTue().size(); i++) {
            String a = (timetable.getTue().get(i).substring(0, 4).equals(subCode)) ? subjectName : "-";
            tueFilter.add(i, a);
        }
        List<String> wedFilter = new ArrayList<>();
        for (int i = 0; i < timetable.getWed().size(); i++) {
            String a = (timetable.getWed().get(i).substring(0, 4).equals(subCode)) ? subjectName : "-";
            wedFilter.add(i, a);
        }
        List<String> thuFilter = new ArrayList<>();
        for (int i = 0; i < timetable.getThu().size(); i++) {
            String a = (timetable.getThu().get(i).substring(0, 4).equals(subCode)) ? subjectName : "-";
            thuFilter.add(i, a);
        }
        List<String> friFilter = new ArrayList<>();

        for (int i = 0; i < timetable.getFri().size(); i++) {
            String a = (timetable.getFri().get(i).substring(0, 4).equals(subCode)) ? subjectName : "-";
            friFilter.add(i, a);
        }
        tableMask.setMon(monFilter);
        tableMask.setTue(tueFilter);
        tableMask.setWed(wedFilter);
        tableMask.setThu(thuFilter);
        tableMask.setFri(friFilter);

        if (tableMask.getMon().size() > 7) {
            List<String> monOperation = new ArrayList<>();
            List<String> tueOperation = new ArrayList<>();
            List<String> wedOperation = new ArrayList<>();
            List<String> thuOperation = new ArrayList<>();
            List<String> friOperation = new ArrayList<>();

            for (int i = 0; i < tableMask.getMon().size() / 2; i++) {
                String el = tableMask.getMon().get(i) + tableMask.getMon().get(i + 6);
                String andEl = el.equals("--") ? "-" :
                        (tableMask.getMon().get(i+6).equals("-"))? subjectName + "문과":subjectName + "이과";
                monOperation.add(i, andEl);
            }
            for (int i = 0; i < tableMask.getTue().size() / 2; i++) {
                String el = tableMask.getTue().get(i) + tableMask.getTue().get(i + 6);
                String andEl = el.equals("--") ? "-" :
                        (tableMask.getTue().get(i+6).equals("-"))? subjectName + " 문과":subjectName + " 이과";
                tueOperation.add(i, andEl);
            }
            for (int i = 0; i < tableMask.getWed().size() / 2; i++) {
                String el = tableMask.getWed().get(i) + tableMask.getWed().get(i + 6);
                String andEl = el.equals("--") ? "-" :
                        (tableMask.getWed().get(i+6).equals("-"))? subjectName + " 문과":subjectName + " 이과";
                wedOperation.add(i, andEl);
            }
            for (int i = 0; i < tableMask.getThu().size() / 2; i++) {
                String el = tableMask.getThu().get(i) + tableMask.getThu().get(i + 6);
                String andEl = el.equals("--") ? "-" :
                        (tableMask.getFri().get(i+6).equals("-"))? subjectName + " 문과":subjectName + " 이과";
                thuOperation.add(i, andEl);
            }
            for (int i = 0; i < tableMask.getFri().size() / 2; i++) {
                String el = tableMask.getFri().get(i) + tableMask.getFri().get(i + 6);
                String andEl = el.equals("--") ? "-" :
                        (tableMask.getFri().get(i+6).equals("-"))? subjectName + " 문과":subjectName + " 이과";
                friOperation.add(i, andEl);
            }
            tableMask.setMon(monOperation);
            tableMask.setTue(tueOperation);
            tableMask.setWed(wedOperation);
            tableMask.setThu(thuOperation);
            tableMask.setFri(friOperation);
            return tableMask;

        } else return tableMask;
    }
    private TimetableVO BasicTTimetable(int userCode) {
        TimetableVO tableMask = new TimetableVO();
        TimetableVO timetable = getTimetable(userCode);
        String subCode = tSubjectRepository.findSubjectIdByUserCode(userCode);
        String subName = tSubjectRepository.getSubjectNameByUserCode(userCode);

        List<String> monFilter = new ArrayList<>();
        for (int i = 0; i < timetable.getMon().size(); i++) {
            String a = (timetable.getMon().get(i).substring(0, 4).equals(subCode)) ? subCode : "-";
            monFilter.add(i, a);
        }
        List<String> tueFilter = new ArrayList<>();
        for (int i = 0; i < timetable.getTue().size(); i++) {
            String a = (timetable.getTue().get(i).substring(0, 4).equals(subCode)) ? subCode : "-";
            tueFilter.add(i, a);
        }
        List<String> wedFilter = new ArrayList<>();
        for (int i = 0; i < timetable.getWed().size(); i++) {
            String a = (timetable.getWed().get(i).substring(0, 4).equals(subCode)) ? subCode : "-";
            wedFilter.add(i, a);
        }
        List<String> thuFilter = new ArrayList<>();
        for (int i = 0; i < timetable.getThu().size(); i++) {
            String a = (timetable.getThu().get(i).substring(0, 4).equals(subCode)) ? subCode : "-";
            thuFilter.add(i, a);
        }
        List<String> friFilter = new ArrayList<>();

        for (int i = 0; i < timetable.getFri().size(); i++) {
            String a = (timetable.getFri().get(i).substring(0, 4).equals(subCode)) ? subCode : "-";
            friFilter.add(i, a);
        }
        tableMask.setMon(monFilter);
        tableMask.setTue(tueFilter);
        tableMask.setWed(wedFilter);
        tableMask.setThu(thuFilter);
        tableMask.setFri(friFilter);

        if (tableMask.getMon().size() > 7) {
            List<String> monOperation = new ArrayList<>();
            List<String> tueOperation = new ArrayList<>();
            List<String> wedOperation = new ArrayList<>();
            List<String> thuOperation = new ArrayList<>();
            List<String> friOperation = new ArrayList<>();

            for (int i = 0; i < tableMask.getMon().size() / 2; i++) {
                String el = tableMask.getMon().get(i) + tableMask.getMon().get(i + 6);
                String andEl = el.equals("--") ? "-" :
                        (tableMask.getMon().get(i+6).equals("-"))? subCode + "1":subCode + "2";
                monOperation.add(i, andEl);
            }
            for (int i = 0; i < tableMask.getTue().size() / 2; i++) {
                String el = tableMask.getTue().get(i) + tableMask.getTue().get(i + 6);
                String andEl = el.equals("--") ? "-" :
                        (tableMask.getTue().get(i+6).equals("-"))? subCode + "1":subCode + "2";
                tueOperation.add(i, andEl);
            }
            for (int i = 0; i < tableMask.getWed().size() / 2; i++) {
                String el = tableMask.getWed().get(i) + tableMask.getWed().get(i + 6);
                String andEl = el.equals("--") ? "-" :
                        (tableMask.getWed().get(i+6).equals("-"))? subCode + "1":subCode + "2";
                wedOperation.add(i, andEl);
            }
            for (int i = 0; i < tableMask.getThu().size() / 2; i++) {
                String el = tableMask.getThu().get(i) + tableMask.getThu().get(i + 6);
                String andEl = el.equals("--") ? "-" :
                        (tableMask.getFri().get(i+6).equals("-"))? subCode + "1":subCode + "2";
                thuOperation.add(i, andEl);
            }
            for (int i = 0; i < tableMask.getFri().size() / 2; i++) {
                String el = tableMask.getFri().get(i) + tableMask.getFri().get(i + 6);
                String andEl = el.equals("--") ? "-" :
                        (tableMask.getFri().get(i+6).equals("-"))? subCode + "1":subCode + "2";
                friOperation.add(i, andEl);
            }
            tableMask.setMon(monOperation);
            tableMask.setTue(tueOperation);
            tableMask.setWed(wedOperation);
            tableMask.setThu(thuOperation);
            tableMask.setFri(friOperation);
            return tableMask;

        } else return tableMask;
    }
    @Override
    public List<Schedule> getSTimetable(int userCode) {
        if (userRepository.findByUserCode(userCode).getHomeClass() < 9) division = 1;
        else division = 2;
        int checker = Integer.parseInt(String.valueOf(userRepository.findByUserCode(userCode).getCurGrade()) + String.valueOf(division));

        return scheduleRepository.studentTimetable(checker);
    }


    //student
    @Override
    public List<Object> getSUserTimetable(int userCode) {
        if (userRepository.findByUserCode(userCode).getHomeClass() < 9) division = 1;
        else division = 2;
        int checker = Integer.parseInt(String.valueOf(userRepository.findByUserCode(userCode).getCurGrade()) + String.valueOf(division));

        return scheduleRepository.SUserTimetable(checker);
    }

    @Override
    public Long findId(Schedule item) {
        return null;
    }

    @Override
    public DndTimetableVO getDndTimetable(int userCode) {
        DndTimetableVO dndTimetableVO = new DndTimetableVO();
        dndTimetableVO.setDndMon(dndTimetableMEl(userCode));
        dndTimetableVO.setDndTue(dndTimetableTEl(userCode));
        dndTimetableVO.setDndWed(dndTimetableWEl(userCode));
        dndTimetableVO.setDndThu(dndTimetableThEl(userCode));
        dndTimetableVO.setDndFri(dndTimetableFEl(userCode));
        return dndTimetableVO;
    }

    @Override
    public void updateOne(DndTimetableEl payload, int userCode) {
        if (userRepository.findByUserCode(userCode).getHomeClass() < 9) division = 1;
        else division = 2;
        int checker = Integer.parseInt(String.valueOf(userRepository.findByUserCode(userCode).getCurGrade()) + String.valueOf(division));
        String updatedCode = "";

        if(payload.getSubjectName().equals("-"))  updatedCode = "null" + division;
        else updatedCode = payload.getSubjectCode()+ division;


        String day = payload.getDay();
        switch (day){
            case "mon" : scheduleRepository.updateMOne(checker,payload.getPeriod(),updatedCode); break;
            case "tue" : scheduleRepository.updateTOne(checker,payload.getPeriod(),updatedCode); break;
            case "wed" : scheduleRepository.updateWOne(checker,payload.getPeriod(),updatedCode); break;
            case "thu" : scheduleRepository.updateThOne(checker,payload.getPeriod(),updatedCode); break;
            case "fri" : scheduleRepository.updateFOne(checker,payload.getPeriod(),updatedCode); break;
        }
    }

    private List<DndTimetableEl> dndTimetableMEl(int userCode) {
        List<DndTimetableEl> dndMel = new ArrayList<>();
        TimetableVO timetableVO = BasicTTimetable(userCode);
        for(int i = 0; i < 6; i++){
            DndTimetableEl dndTimetableEl = new DndTimetableEl();
            dndTimetableEl.setId("mon"+String.valueOf(i));
            dndTimetableEl.setDay("mon");
            dndTimetableEl.setPeriod(i+1);
            dndTimetableEl.setSubjectCode(timetableVO.getMon().get(i));
            dndTimetableEl.setSubjectName(timetableVO.getMon().get(i).equals("-")? "-": tSubjectRepository.getSubjectNameBySubjectCode(timetableVO.getMon().get(i).substring(0,4)));
            dndMel.add(i,dndTimetableEl);
        }
        return dndMel;
    }
    private List<DndTimetableEl> dndTimetableTEl(int userCode) {
        List<DndTimetableEl> dndTel = new ArrayList<>();
        TimetableVO timetableVO = BasicTTimetable(userCode);
        for(int i = 0; i < 6; i++){
            DndTimetableEl dndTimetableEl = new DndTimetableEl();
            dndTimetableEl.setId("tue"+String.valueOf(i));
            dndTimetableEl.setDay("tue");
            dndTimetableEl.setPeriod(i+1);
            dndTimetableEl.setSubjectCode(timetableVO.getTue().get(i));
            dndTimetableEl.setSubjectName(timetableVO.getTue().get(i).equals("-")? "-": tSubjectRepository.getSubjectNameBySubjectCode(timetableVO.getTue().get(i).substring(0,4)));
            dndTel.add(i,dndTimetableEl);
        }
        return dndTel;
    }
    private List<DndTimetableEl> dndTimetableWEl(int userCode) {
        List<DndTimetableEl> dndWel = new ArrayList<>();
        TimetableVO timetableVO = BasicTTimetable(userCode);
        
        for(int i = 0; i < 6; i++) {
            DndTimetableEl dndTimetableEl = new DndTimetableEl();
            dndTimetableEl.setId("wed" + String.valueOf(i));
            dndTimetableEl.setDay("wed");
            dndTimetableEl.setPeriod(i + 1);
            dndTimetableEl.setSubjectCode(timetableVO.getWed().get(i));
            dndTimetableEl.setSubjectName(timetableVO.getWed().get(i).equals("-") ? "-" : tSubjectRepository.getSubjectNameBySubjectCode(timetableVO.getWed().get(i).substring(0, 4)));
            dndWel.add(i,dndTimetableEl);
        }
        return dndWel;
    }
    private List<DndTimetableEl> dndTimetableThEl(int userCode) {
        List<DndTimetableEl> dndThel = new ArrayList<>();
        TimetableVO timetableVO = BasicTTimetable(userCode);
        for(int i = 0; i < 6; i++){
            DndTimetableEl dndTimetableEl = new DndTimetableEl();
            dndTimetableEl.setId("thu"+String.valueOf(i));
            dndTimetableEl.setDay("thu");
            dndTimetableEl.setPeriod(i+1);
            dndTimetableEl.setSubjectCode(timetableVO.getThu().get(i));
            dndTimetableEl.setSubjectName(timetableVO.getThu().get(i).equals("-")? "-": tSubjectRepository.getSubjectNameBySubjectCode(timetableVO.getThu().get(i).substring(0,4)));
            dndThel.add(i,dndTimetableEl);
        }
        return dndThel;
    }
    private List<DndTimetableEl> dndTimetableFEl(int userCode) {
        List<DndTimetableEl> dndFel = new ArrayList<>();
        TimetableVO timetableVO = BasicTTimetable(userCode);
        for(int i = 0; i < 6; i++){
            DndTimetableEl dndTimetableEl = new DndTimetableEl();
            dndTimetableEl.setId("fri"+String.valueOf(i));
            dndTimetableEl.setDay("fri");
            dndTimetableEl.setPeriod(i+1);
            dndTimetableEl.setSubjectCode(timetableVO.getFri().get(i));
            dndTimetableEl.setSubjectName(timetableVO.getFri().get(i).equals("-")? "-": tSubjectRepository.getSubjectNameBySubjectCode(timetableVO.getFri().get(i).substring(0,4)));
            dndFel.add(i,dndTimetableEl);
        }
        return dndFel;
    }
}