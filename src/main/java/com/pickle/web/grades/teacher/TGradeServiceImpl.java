package com.pickle.web.grades.teacher;

import com.pickle.web.commons.Proxy;
import com.pickle.web.grades.Grade;
import com.pickle.web.grades.GradeVO;
import com.pickle.web.grades.Mock;
import com.pickle.web.grades.MockRepository;
import com.pickle.web.subjects.Subject;
import com.pickle.web.users.User;
import com.pickle.web.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

interface TGradeService {
    User findUserByUserInfo(String userInfo);

    User findUserByUserInfoWithSchoolCode(String userInfo);

    List<HashMap> getScoreByUser(String userInfo);

    void insert(HashMap<?, ?> map);

    void saveGrade(User user, String subName, int semesterCode, int score);

    List<HashMap> getClassGrade(int schoolCode, int grade, int homeClass);

    List<HashMap> getTotalGrade(int schoolCode, int grade);

    HashMap<String, List<HashMap>> getScatter(int schoolCode, int curGrade, int homeClass);

    HashMap<String, List> getClassRank();

    List<HashMap> getGradeBySemester(List<GradeVO> list);

    List<HashMap> getLineChart(List<GradeVO> list);

    List<HashMap> getRadarChart(List<GradeVO> list);

    void makeScores();

    List<Mock> getMockByUser(String userInfo);
}

@Service
public class TGradeServiceImpl implements TGradeService {
    @Autowired
    Proxy proxy;
    private final TGradeRepository repository;
    private final MockRepository mockRepository;
    private final UserRepository userRepository;
    private List<GradeVO> classList;
    private String curYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

    public TGradeServiceImpl(TGradeRepository repository, MockRepository mockRepository, UserRepository userRepository) {
        this.repository = repository;
        this.mockRepository = mockRepository;
        this.userRepository = userRepository;
    }

    public List<String> getSubjectNames(List<Grade> list) {
        List<String> subjectNames = new ArrayList<>();
        for (Grade i : list) {
            String subName = i.getSubject().getSubjectName();
            if (!subjectNames.contains(subName)) {
                subjectNames.add(subName);
            }
        }
        return subjectNames;
    }

    public List<HashMap> getResult(int memberCount, List<Grade> result) {
        List<String> subNames = getSubjectNames(result);
        List<HashMap> maplist = new ArrayList<>();
        for (String i : subNames) {
            int score = 0;
            for (Grade j : result) {
                if (i.equals(j.getSubject().getSubjectName())) score += j.getScore();
            }
            score = score / memberCount / repository.getSemesterCount();
            HashMap<String, String> map = new HashMap<>();
            map.put("sub", i);
            map.put("score", String.valueOf(score));
            maplist.add(map);
        }
        return maplist;
    }

    public List<HashMap<String, String>> getTotalList(List<GradeVO> curList, List<Integer> userCodes) {
        //한 학생의 평균 성적
        List<HashMap<String, Integer>> avg = new ArrayList<>();
        for (Integer i : userCodes) {
            int score = 0;
            HashMap<String, Integer> map = new HashMap<>();
            for (GradeVO vo : curList) {
                if (vo.getUserCode() == i) score += vo.getScore();
            }
            map.put("userCode", i);
            map.put("score", score / 9);
            avg.add(map);
        }
        List<Integer> scores = new ArrayList<>();
        for (HashMap<String, Integer> map : avg) {
            scores.add((Integer) map.get("score"));
        }
        scores.sort(Collections.reverseOrder());
        //모든 학생 랭크 매기기
        List<HashMap<String, String>> avgList = new ArrayList<>();
        for (int i = 0; i < scores.size(); i++) {
            for (HashMap<String, Integer> map : avg) {
                if (map.get("score").equals(scores.get(i))) {
                    HashMap<String, String> m = new HashMap<>();
                    m.put("score", String.valueOf(scores.get(i)));
                    m.put("userCode", (String.valueOf(map.get("userCode"))));
                    m.put("rank", String.valueOf(i + 1));
                    avgList.add(m);
                }
            }
        }
        for (HashMap<String, String> map : avgList) {
            for (GradeVO vo : curList) {
                if (map.get("userCode").equals(String.valueOf(vo.getUserCode()))) map.put("name", vo.getName());
            }
        }
        //실제 리턴할 값인 5등까지의 리스트
        List<HashMap<String, String>> totalResult = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            totalResult.add(avgList.get(i));
        }
        return totalResult;
    }

    public List<HashMap> getClassList(List<GradeVO> curList) {
        //과목별 성적
        String[] subNames = {"국어", "영어", "수학", "생활과윤리", "경제", "한국사", "생명과학", "물리", "제2외국어"};
        List<HashMap<String, List<Integer>>> scoresBySub = new ArrayList<>();
        for (int i = 0; i < subNames.length; i++) {
            HashMap<String, List<Integer>> map = new HashMap<>();
            List<Integer> scores = new ArrayList<>();
            for (GradeVO vo : curList) {
                if (vo.getSubjectName().startsWith(subNames[i])) scores.add(vo.getScore());
            }
            scores.sort(Collections.reverseOrder());
            map.put(subNames[i], scores);
            scoresBySub.add(map);
        }
        List<HashMap> result = new ArrayList<>();
        for (int i = 0; i < subNames.length; i++) {
            int[] nnn = new int[5];
            for (int j = 0; j < nnn.length; j++) {
                nnn[j] = scoresBySub.get(i).get(subNames[i]).get(j);
            }
            List<HashMap> list = new ArrayList<>();
            for (int j = 0; j < nnn.length; j++) {
                for (GradeVO vo : curList) {
                    if (vo.getSubjectName().startsWith(subNames[i]) &&
                            vo.getScore() == nnn[j]) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("name", vo.getName());
                        map.put("score", String.valueOf(vo.getScore()));
                        map.put("rank", String.valueOf(j + 1));
                        list.add(map);
                    }
                }
            }
            HashMap<String, List> map = new HashMap<>();
            map.put("sub", Collections.singletonList(subNames[i]));
            map.put("rank", list);
            result.add(map);
        }
        return result;
    }

    @Override
    public User findUserByUserInfo(String userInfo) {
        int schoolCode = Integer.parseInt(userInfo.substring(0, 4));
        int curGrade = Integer.parseInt(userInfo.substring(10, 11));
        int homeClass = Integer.parseInt(userInfo.substring(11, 13));
        String name = userInfo.substring(13);
        return repository.findUserByUserInfo(schoolCode, curGrade, homeClass, name);
    }

    @Override
    public User findUserByUserInfoWithSchoolCode(String userInfo) {
        int schoolCode = Integer.parseInt(userInfo.substring(0, 4));
        int curGrade = Integer.parseInt(userInfo.substring(4, 5));
        int homeClass = Integer.parseInt(userInfo.substring(5, 7));
        String name = userInfo.substring(7);
        return repository.findUserByUserInfo(schoolCode, curGrade, homeClass, name);
    }

    @Override
    public List<HashMap> getScoreByUser(String userInfo) {
        int semesterCode = Integer.parseInt(userInfo.substring(4, 10));
        User u = findUserByUserInfo(userInfo);
        List<Grade> list = repository.getScoreByUser(u, semesterCode);
        List<HashMap> result = new ArrayList<>();
        for (Grade i : list) {
            HashMap<String, String> map = new HashMap();
            map.put("sub", i.getSubject().getSubjectName());
            map.put("score", String.valueOf(i.getScore()));
            result.add(map);
        }
        return result;
    }

    @Override
    public void insert(HashMap<?, ?> map) {
        String userInfo = String.valueOf(map.get("userInfo"));
        User u = findUserByUserInfo(userInfo);
        int semesterCode = Integer.parseInt(userInfo.substring(4, 10));
        String[] subNames = {"kor", "eng", "mat", "phl", "eco", "his", "bio", "phy", "for"};
        for (int i = 0; i < subNames.length; i++) {
            saveGrade(u, subNames[i], semesterCode, Integer.parseInt(String.valueOf(map.get(subNames[i]))));
        }
    }

    @Override
    public void saveGrade(User user, String subName, int semesterCode, int score) {
        Grade grade = new Grade();
        String subjectCode = subName + user.getCurGrade();
        Subject subject = repository.findSubjectId(subjectCode);
        grade.setUser(user);
        grade.setSubject(subject);
        grade.setSemesterCode(semesterCode);
        grade.setScore(score);
        Grade g = repository.findByUserAndSubject(user, subjectCode, semesterCode);
        if (g == null) {
            repository.save(grade);
        } else {
            repository.update(grade);
        }
    }

    @Override
    public List<HashMap> getClassGrade(int schoolCode, int grade, int homeClass) {
        int memberCount = repository.getClassMemberCount(schoolCode, grade, homeClass);
        List<Grade> result = repository.getClassGrade(schoolCode, grade, homeClass);
        this.classList = proxy.convertToGradeVo(result);
        return getResult(memberCount, result);
    }

    @Override
    public List<HashMap> getTotalGrade(int schoolCode, int grade) {
        int memberCount = repository.getUserCountByGrade(schoolCode, grade);
        List<Grade> result = repository.getTotalGrade(schoolCode, grade);
        return getResult(memberCount, result);
    }

    @Override
    @Transactional
    public HashMap<String, List<HashMap>> getScatter(int schoolCode, int curGrade, int homeClass) {
        int classCount = 15;
        List<HashMap> mapList = repository.getAvg(schoolCode, curGrade, classCount);
        Integer hc = homeClass;
        List<HashMap> classResult = new ArrayList<>();
        List<HashMap> totalResult = new ArrayList<>();
        for (HashMap map : mapList) {
            if (map.get("class") == hc) {
                classResult.add(map);
            } else {
                totalResult.add(map);
            }
        }
        HashMap<String, List<HashMap>> result = new HashMap();
        result.put("classResult", classResult);
        result.put("totalResult", totalResult);
        return result;
    }

    @Override
    public HashMap<String, List> getClassRank() {
        List<GradeVO> curList = new ArrayList<>();
        for (GradeVO i : classList) {
            if (i.getSemesterCode() == 202012) curList.add(i);
        }
        List<Integer> userCodes = new ArrayList<>();
        for (GradeVO vo : curList) {
            if (!userCodes.contains(vo.getUserCode())) userCodes.add(vo.getUserCode());
        }
        HashMap<String, List> result = new HashMap<>();
        result.put("totalRank", getTotalList(curList, userCodes));
        result.put("subRank", getClassList(curList));
        return result;
    }

    @Override
    public List<HashMap> getGradeBySemester(List<GradeVO> list) {
        List<GradeVO> curList = new ArrayList<>();
        for (GradeVO vo : list) {
            if (vo.getSemesterCode() / 100 == Integer.parseInt(curYear)) curList.add(vo);
        }
        List<HashMap> result = new ArrayList<>();
        int[] semesters = {11, 12, 21, 22};
        for (int i = 0; i < semesters.length; i++) {
            HashMap<String, List> m = new HashMap<>();
            List<Integer> temp = new ArrayList<>();
            for (GradeVO vo : curList) {
                if (vo.getSemesterCode() % 100 == semesters[i]) {
                    temp.add(vo.getScore());
                }
            }
            m.put("semesterCode", Collections.singletonList(String.valueOf(curYear + semesters[i])));
            m.put("score", temp);
            result.add(m);
        }
        return result;


    }

    @Override
    public List<HashMap> getLineChart(List<GradeVO> list) {
        List<HashMap> result = new ArrayList<>();
        String[] subNames = {"국어", "영어", "수학", "생활과윤리", "경제", "한국사", "생명과학", "물리", "제2외국어"};
        for (int i = 0; i < subNames.length; i++) {
            HashMap<String, List> m = new HashMap<>();
            List<Integer> temp = new ArrayList<>();
            for (GradeVO vo : list) {
                if (vo.getSubjectName().startsWith(subNames[i])) temp.add(vo.getScore());
            }
            m.put("sub", Collections.singletonList(subNames[i]));
            m.put("score", temp);
            result.add(m);
        }
        return result;
    }

    @Override
    public List<HashMap> getRadarChart(List<GradeVO> list) {
        List<HashMap> result = new ArrayList<>();
        String[] subNames = {"국어", "영어", "수학"};
        //{name: radarOne, scores: [10, 20, 30]}, {name: radarAll, scores: [10, 20, 30]}
        List<GradeVO> curList = new ArrayList<>();
        for (GradeVO vo : list) {
            if (vo.getSemesterCode() / 100 == Integer.parseInt(curYear)) curList.add(vo);
        }

        HashMap<String, List> m1 = new HashMap<>();
        List<Integer> temp1 = new ArrayList<>();
        for (int i = 0; i < subNames.length; i++) {
            for (GradeVO vo : list) {
                if (vo.getSemesterCode() == 202012 && vo.getSubjectName().startsWith(subNames[i]))
                    temp1.add(vo.getScore());
            }
        }
        m1.put("name", Collections.singletonList("radarOne"));
        m1.put("score", temp1);
        result.add(m1);
        HashMap<String, List> m2 = new HashMap<>();
        List<Integer> temp2 = new ArrayList<>();
        for (int i = 0; i < subNames.length; i++) {
            int score = 0;
            for (GradeVO vo : list) {
                if (vo.getSubjectName().startsWith(subNames[i]) && String.valueOf(vo.getSemesterCode()).startsWith("2020"))
                    score += vo.getScore();
            }
            temp2.add(score / 2);
        }
        m2.put("name", Collections.singletonList("radarAll"));
        m2.put("score", temp2);
        result.add(m2);
        return result;
    }

    @Override
    public void makeScores() {
        String userCode = "100018";
        for (int i = 1; i <= 375; i++) {
            String uc = "";
            if (i < 10) {
                uc = String.format("%s%d%d%d", userCode, 0, 0, i);
            } else if (i < 100) {
                uc = String.format("%s%d%d", userCode, 0, i);
            } else {
                uc = String.format("%s%d", userCode, i);
            }
            User user = userRepository.findByUserCode(Integer.parseInt(uc));
            String[] subNames = {"국", "수", "영", "탐1", "탐2"};
            for(int a=0; a< subNames.length; a++){
                for (int j = 1; j <= 2; j++) {
                    Mock mock = new Mock();
                    mock.setSubject(subNames[a]);
                    mock.setUser(user);
                    mock.setSemester_code(j);
                    mock.setScore((int) (Math.random() * 100));
                    mockRepository.save(mock);
                }
            }
        }
    }

    @Override
    public List<Mock> getMockByUser(String userInfo) {
        int schoolCode = Integer.parseInt(userInfo.substring(0, 4));
        int semesterCode = Integer.parseInt(userInfo.substring(4, 5));
        int curGrade = Integer.parseInt(userInfo.substring(5, 6));
        int homeClass = Integer.parseInt(userInfo.substring(6, 8));
        String name = userInfo.substring(8);
        return repository.getMocks(repository.findUserByUserInfo(schoolCode, curGrade, homeClass, name), semesterCode);
    }
}
