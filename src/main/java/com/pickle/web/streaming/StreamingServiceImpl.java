package com.pickle.web.streaming;
import com.pickle.web.users.UserRepository;
import com.querydsl.core.Tuple;
import org.springframework.stereotype.Service;

import java.util.*;

interface StreamingService{
    String findSClassCode(int userCode);
    List<?> findSStudentList(int userCode);
    String findTClassCode(int userCode);
    List<?> findTStudentList(String tClassCode);

}
@Service
public class StreamingServiceImpl implements StreamingService{
    private final StreamingRepository streamingRepository;
    private final UserRepository userRepository;

    public StreamingServiceImpl(StreamingRepository streamingRepository, UserRepository userRepository) {
        this.streamingRepository = streamingRepository;
        this.userRepository = userRepository;
    }


    @Override
    public String findSClassCode(int userCode) {
            Tuple user = streamingRepository.findSUserData(userCode);
            String checker = user.get(1, Integer.class)<9? String.format("%s%s", user.get(0,Integer.class),1): String.format("%s%s", user.get(0,Integer.class),1);
            String[] currentClass = streamingRepository.findSSchedule(checker).toString().split(", ");
        return currentClass[0].concat(currentClass[1]).substring(1,7);
    }

    @Override
    public List<?> findSStudentList(int userCode) {
        Tuple user = streamingRepository.findSUserData(userCode);
        String checker = String.format("%s%s", user.get(0,Integer.class),user.get(1,Integer.class));
        List<Tuple> userList = (List<Tuple>) streamingRepository.findSStudentList(checker);
        List<String> studentList = new ArrayList<>();
        for (Tuple users : userList){
            if (!users.get(0, Integer.class).toString().substring(4, 6).equals("00")){
                studentList.add(users.toString());
            };
        }
        return studentList;
    }

    @Override
    public String findTClassCode(int userCode) {
        String subCode = streamingRepository.findTSubject(userCode);
        int division = 0;
        String checker = "";
        String classCode ="";
        switch (subCode.substring(0, 3)) {
            case "kor": case "eng": case "mat": case "his": case "for":
                if (userRepository.findByUserCode(userCode).getHomeClass() < 9) division = 1;
                else division = 2;
                checker = userRepository.findByUserCode(userCode).getCurGrade() + String.valueOf(division);
                String[] currentClass = streamingRepository.findSSchedule(checker).toString().split(", ");
                classCode = currentClass[0].concat(currentClass[1]).substring(1,7);
                break;
            case "phl": case "eco": case "bio": case "phy":
                List<Tuple> tSchedules = streamingRepository.findTSchedule();
                for (Tuple tSchedule : tSchedules ){
                    if (tSchedule.get(0,String.class).substring(0,4).equals(subCode)){
                       classCode = tSchedule.get(0,String.class).concat(Objects.requireNonNull(tSchedule.get(1, Integer.class).toString()));
                       break;
                    }
                } 
        }
        return classCode;
    }

    @Override
    public List<?> findTStudentList(String tClassCode) {
       Integer checker = streamingRepository.findTStudentList(tClassCode);
       List<Tuple> userList = (List<Tuple>) streamingRepository.findSStudentList(checker.toString());
        List<String> studentList = new ArrayList<>();
        for (Tuple users : userList){
            if (!users.get(0, Integer.class).toString().substring(4, 6).equals("00")){
                studentList.add(users.toString());
            };
        }
        return studentList;
    }

}
