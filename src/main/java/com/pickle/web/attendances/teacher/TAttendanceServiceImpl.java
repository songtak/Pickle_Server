package com.pickle.web.attendances.teacher;

import com.pickle.web.attendances.Attendance;
import com.pickle.web.commons.JpaService;
import com.pickle.web.users.User;
import com.pickle.web.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

interface TAttendanceService extends JpaService {
    List<Attendance> findAll();
    List<AttendanceList> getDetailList(int curGrade, int homeClass,int year);
    List<Integer> getSingleDay(LocalDate localDate,int grade, int homeclass);
    void update(int userCode, LocalDate localDate , int checkPresent);
    void updateTardy(int userCode, LocalDate localDate);
}
    @Service
    @RequiredArgsConstructor
    public class TAttendanceServiceImpl implements TAttendanceService {
        private final TAttendanceRepository aRepository;
        private final UserRepository userRepository;

        @Override
        public Optional<Attendance> findById(String userCode) {
            return aRepository.findById(Long.valueOf(userCode));
        }
        @Override
        public List<Attendance> findAll() {
            return aRepository.findAll();
        }
        @Override
        public int count() {
            return (int) aRepository.count();
        }
        @Override
        public void delete(String id) {
            aRepository.deleteById(Long.valueOf(id));
        }
        @Override
        public boolean exists(String id) {
            return aRepository.existsById(Long.valueOf(id));
        }

        @Override @Transactional
        public List<AttendanceList> getDetailList(int curGrade, int homeClass,int year) {

            LocalDate fromDate = LocalDate.of(year,01,01);
            LocalDate toDate = LocalDate.of(year,01,31);
            List<User> users = aRepository.getGroup(curGrade, homeClass);
            List<AttendanceList> resultList = new ArrayList<>();
            users.forEach(user -> {
                List<Integer> months = new ArrayList<>();
                AttendanceList oneUserAttendance = new AttendanceList();
                oneUserAttendance.setUserCode(user.getUserCode());
                oneUserAttendance.setName(user.getName());
                for(int i = 0;i < 12; i++) {
                    LocalDate startDate = fromDate.plusMonths(i);
                    LocalDate endDate = toDate.plusMonths(i);
                    months.add(aRepository.getMonthlyAbsentCount(user.getUserCode(),startDate,endDate));
                }
                oneUserAttendance.setMonths(months);
                resultList.add(oneUserAttendance);
            });
            return resultList ;
        }

        //Confirmed working properly
        @Override
        public List<Integer> getSingleDay(LocalDate localDate,int grade, int homeclass) {
            int countStudent = aRepository.getGroupCount(grade,homeclass);
            List<Integer> list = new ArrayList<>();
            list.add(aRepository.getPresentCount(localDate, grade, homeclass)/countStudent*100);
            list.add(aRepository.getAbsentCount(localDate, grade, homeclass)/countStudent*100);
            list.add(aRepository.getTardyCount(localDate, grade, homeclass)/countStudent*100);
            return list;
        }
        //Confirmed working properly
        @Override
        public void update(int userCode, LocalDate localDate, int checkPresent) {
            if(checkPresent != 0) aRepository.updatePresent(userCode, localDate);
            else aRepository.updateAbsent(userCode,localDate);
        }

        //Confirmed working properly
        @Override
        public void updateTardy(int userCode, LocalDate localDate) {
            List<Integer> checker = aRepository.checkAbsent(userCode,localDate);
            if (checker.get(0) != 0){
                aRepository.updateTardy(userCode,localDate);
            }
        }
    }

