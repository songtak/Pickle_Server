package com.pickle.web.grades.student;


import com.pickle.web.commons.Proxy;
import com.pickle.web.grades.GradeVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.jdo.annotations.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


interface ISGradeService{
    List<GradeVO> getStudentGrade();
    int[] getUserScore(int userCode);
    double[] getMainChart(int userCode);

    HashMap<String,int[]> searchUserScore(int userCode, SGradeOptions searchGradeValue);
    HashMap<String,int[]> searchTotalScore(SGradeOptions searchGradeValue);

    int[] getKorGrade(int userCode);
    int[] getEngGrade(int userCode);
    int[] getMatGrade(int userCode);
    int[] getHisGrade(int userCode);
    int[] getPhlGrade(int userCode);
    int[] getEcoGrade(int userCode);
    int[] getPhyGrade(int userCode);
    int[] getBioGrade(int userCode);
    int[] getForGrade(int userCode);

}


@Service @AllArgsConstructor
public class SGradeServiceImpl implements ISGradeService{
    private final SGradeRepository sGradeRepository;
    private final Proxy proxy;

/*    @Override
    public List<HashMap> getStudentGrade(int userCode) {
        return null;
    }*/


    @Override @Transactional
    public List<GradeVO> getStudentGrade() {
        //그 학생의 올해 성적 모두 가져온 것
        return proxy.convertToGradeVo(sGradeRepository.getStudentGrade());
    }


    @Override
    public  int[] getUserScore(int userCode){
        return sGradeRepository.findUserScore(userCode);
    }



    public double[] getMainChart(int userCode){
        int[] list=new int[9];
        String user, semester;
        int userInt, semesterInt, nowSemester;


        user = String.valueOf(userCode).substring(4,6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy");
        String date = LocalDate.now().atStartOfDay().format(formatter);
        Calendar cal = Calendar.getInstance();



        semester = String.valueOf(cal.get(Calendar.YEAR)).substring(2,4);


        userInt = Integer.parseInt(user);
        semesterInt= Integer.parseInt(semester);

        nowSemester = semesterInt - userInt;

        switch (
                nowSemester
        ){
            case 0:  int[] subjectList ={1, 2, 3, 4, 5, 6, 7, 8, 9};
            list=subjectList;
                break;
            case 1: int[] subjectList2 ={10, 11, 12, 13, 14, 15, 16, 17, 18};
                list=subjectList2;
                break;
            case 2: int[] subjectList3 ={19, 20, 21, 22, 23, 24, 25, 26, 27};
                list=subjectList3;
                break;
        }

        double[] subjectScores = new double[9];
        for(int i=0; i<9;i++){
            subjectScores[i]= sGradeRepository.getMainChart(list[i]);
            System.out.println(subjectScores[i]);
        }
        return subjectScores;
    }




    public HashMap<String, int[]> searchUserScore(int userCode, SGradeOptions searchGradeValue) {
        int examLength=searchGradeValue.getExamList().length;
            System.out.println(Arrays.toString(searchGradeValue.getExamList()));
        System.out.println(searchGradeValue.getSelectedGrade());
            System.out.println(Arrays.toString(searchGradeValue.getSubjectList()));
        System.out.println(searchGradeValue.getUserSelectedList());
        HashMap<String,int[]> list= new HashMap<>();

        for(int i=0;i<examLength;i++){
            int[] avgBySemester = new int[searchGradeValue.getSubjectList().length];

            for(int j=0; j<searchGradeValue.getSubjectList().length;j++){
                avgBySemester[j]=sGradeRepository.searchUserScore(
                        userCode,
                        Integer.parseInt("2020"+searchGradeValue.getExamList()[i]),
                        searchGradeValue.getSubjectList()[j]);
            }
            String key;
            switch (searchGradeValue.getExamList()[i]){
                case "11": key="firstFirst"; break;
                case "12": key="firstSecond"; break;
                case "21": key="secondFirst"; break;
                case "22": key="secondSecond"; break;
                default: key="nothing"; break;
            }
                list.put(key,avgBySemester);
        }
        return list;
    }

    public HashMap<String, int[]> searchTotalScore(SGradeOptions searchGradeValue) {
        int examLength=searchGradeValue.getExamList().length;
        HashMap<String,int[]> list= new HashMap<>();
        System.out.println(searchGradeValue);
        for(int i=0;i<examLength;i++){
            int[] avgBySemester = new int[searchGradeValue.getSubjectList().length];
            for(int j=0; j<searchGradeValue.getSubjectList().length;j++){
                avgBySemester[j]=sGradeRepository.searchTotalScore(Integer.parseInt("2020"+searchGradeValue.getExamList()[i]),
                        searchGradeValue.getSubjectList()[j]);
            }
            String key;
            switch (searchGradeValue.getExamList()[i]){
                case "11": key="firstFirst"; break;
                case "12": key="firstSecond"; break;
                case "21": key="secondFirst"; break;
                case "22": key="secondSecond"; break;
                default: key="nothing"; break;
            }
            list.put(key,avgBySemester);
        }
        return list;
    }

    @Override
    public int[] getKorGrade(int userCode) {
        return sGradeRepository.getKorGrade(userCode);
    }

    @Override
    public int[] getEngGrade(int userCode) {
        return sGradeRepository.getEngGrade(userCode);
    }

    @Override
    public int[] getMatGrade(int userCode) {
        return sGradeRepository.getMatGrade(userCode);
    }

    @Override
    public int[] getHisGrade(int userCode) {
        return sGradeRepository.getHisGrade(userCode);
    }

    @Override
    public int[] getPhlGrade(int userCode) {
        return sGradeRepository.getPhlGrade(userCode);
    }

    @Override
    public int[] getEcoGrade(int userCode) {
        return sGradeRepository.getEcoGrade(userCode);
    }

    @Override
    public int[] getPhyGrade(int userCode) {
        return sGradeRepository.getPhyGrade(userCode);
    }

    @Override
    public int[] getBioGrade(int userCode) {
        return sGradeRepository.getBioGrade(userCode);
    }

    @Override
    public int[] getForGrade(int userCode) {
        return sGradeRepository.getForGrade(userCode);
    }





}