package com.pickle.web.users.userproxy;

import com.pickle.web.users.User;
import com.pickle.web.users.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
@RestController
public class MakeUsers {
    private User user;
    private final UserRepository userRepository;

    public MakeUsers(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping("/makeusers") @Transactional
    public void insertUser(){
        User user = null;
        String temp = "";
        for(int i=351; i<=375; i++){
            user = makeUser();
            temp = "100018"+String.valueOf(i);
            user.setUserCode(Integer.parseInt(temp));
            insertFile(user);
        }
    }
    public User makeUser() {
        user = new User();
        user.setSchoolCode(1000);
        user.setCurGrade(3);
        user.setHomeClass(15);
        user.setSchoolName("선유고등학교");
        user.setName(makeName());
        user.setUserId(makeUserId());
        user.setUserPw(makeUserPw());
        user.setEmail(makeEmail());
        user.setPhone(makePhone());
        user.setPositionChecker(1);
        return user;
    }
    public String makeName(){
        List<String> lastName = Arrays.asList("김", "이", "박", "최", "정", "강", "조", "윤", "장", "임", "한", "오", "서", "신");
        List<String> firstName = Arrays.asList("서연", "서윤", "지우", "서현", "민서", "하은", "하윤", "윤서", "지민", "채원", "지윤", "은서", "수아", "다은", "예은", "수빈", "민준", "서준", "예준", "도윤", "시우", "주원", "하준", "지호", "지후", "준서", "준우", "현우", "지훈", "도현", "건우", "우진", "선우", "현준", "서진", "지호", "유준", "주원", "민준", "은우", "지호", "지안", "하린", "지유", "수아");
        Collections.shuffle(lastName);
        Collections.shuffle(firstName);
        return lastName.get(0) + firstName.get(0);
    }
    public String makeShuffle(){
        List<String> makeShuffle = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "n", "m", "o",
                "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "0");
        Collections.shuffle(makeShuffle);
        return makeShuffle.get(0);
    }
    public String makeUserId(){
        return makeShuffle() + makeShuffle() + makeShuffle() + makeShuffle() + makeShuffle() + makeShuffle() + makeShuffle() + makeShuffle();
    }
    public String makeUserPw(){
        return makeShuffle() + makeShuffle() + makeShuffle() + makeShuffle() + makeShuffle() + makeShuffle() + makeShuffle() + makeShuffle();
    }
    public String makeEmail(){
        return makeShuffle() + makeShuffle() + makeShuffle() + makeShuffle() + makeShuffle() + makeShuffle() + "@school.com";
    }
    public String makePhone(){
        int a = 1111, b = 9999;
        BiFunction<Integer, Integer, Integer> f = (t, u) -> (int) (Math.random() * (u - t)) + t;
        int pre = f.apply(a, b);
        int af = f.apply(a, b);
        String tel = "010-" + String.valueOf(pre) + "-" + String.valueOf(af);
        return tel;
    }
    public String makeGardianRelation(){
        List<String> genderText = Arrays.asList("부", "모");
        Collections.shuffle(genderText);
        return genderText.get(0);
    }
    public String makeGender(){
        List<String> genderText = Arrays.asList("M", "F");
        Collections.shuffle(genderText);
        return genderText.get(0);
    }

    public void insertFile(User user){
        userRepository.save(user);
    }
}
