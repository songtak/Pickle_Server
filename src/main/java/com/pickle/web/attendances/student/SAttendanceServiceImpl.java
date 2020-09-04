package com.pickle.web.attendances.student;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


interface ISAttendanceService{
    List<Object> findSAtte(int userCode);
    List<Object> findAtteSum(int userCode);
}



@Service @AllArgsConstructor
public class SAttendanceServiceImpl implements ISAttendanceService{
   private final SAttendanceRepository sAttendanceRepository;


    // user_code로 개인의 값 가져오기
    public List<Object> findSAtte(int userCode){
        List<Object> list = sAttendanceRepository.findStudentAtte(userCode);
        return list;
    }

    public List<Object> findAtteSum(int userCode){
        List<Object> sum = sAttendanceRepository.findStudentAtteSum(userCode);
        return sum;
    }



}
