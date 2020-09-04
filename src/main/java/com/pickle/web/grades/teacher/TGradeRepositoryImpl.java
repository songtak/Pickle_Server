package com.pickle.web.grades.teacher;

import com.pickle.web.commons.Proxy;
import com.pickle.web.grades.Grade;

import static com.pickle.web.grades.QGrade.grade;
import static com.pickle.web.subjects.QSubject.subject;
import static com.pickle.web.users.QUser.user;
import static com.pickle.web.grades.QMock.mock;

import com.pickle.web.grades.Mock;
import com.pickle.web.subjects.Subject;
import com.pickle.web.users.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.*;

interface ITGradeRepository {
    int getClassMemberCount(int schoolCode, int curGrade, int homeClass);

    int getSemesterCount();

    List<Grade> getClassGrade(int schoolCode, int curGrade, int homeClass);

    List<Grade> getStudentGrade(long id);

    User findUserByUserInfo(int schoolCode, int curGrade, int homeClass, String name);

    List<Integer> getClassBySchoolCode(int schoolCode, int grade);

    List<String> getStudentBySchoolCodeAndGrade(int schoolCode, int grade, int homeClass);

    List<Grade> getScoreByUser(User u, int semesterCode);

    Subject findSubjectId(String subjectCode);

    void update(Grade g);

    Grade findByUserAndSubject(User user, String subjectCode, int semesterCode);

    int getUserCountByGrade(int schoolCode, int curGrade);

    List<Grade> getTotalGrade(int schoolCode, int curGrade);

    List<HashMap> getAvg(int schoolCode, int curGrade, int classCount);

    List<Grade> getTotalGradeByUser(User u);

    List<Mock> getMocks(User u, int semesterCode);
}

@Repository
public class TGradeRepositoryImpl extends QuerydslRepositorySupport implements ITGradeRepository {
    @Autowired
    Proxy proxy;
    private final JPAQueryFactory queryFactory;
    private String curYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

    public TGradeRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Grade.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public int getClassMemberCount(int schoolCode, int curGrade, int homeClass) {
        return (int) queryFactory.selectFrom(user)
                .where(user.homeClass.eq(homeClass)
                        .and(user.schoolCode.eq(schoolCode))
                        .and(user.curGrade.eq(curGrade))
                        .and(user.positionChecker.eq(1)))
                .fetchCount();
    }

    @Override
    public int getSemesterCount() {
        long result = queryFactory.selectDistinct(grade.semesterCode).from(grade)
                .where(grade.semesterCode.stringValue().startsWith(curYear)
                        .and(grade.user.curGrade.eq(1)))
                .fetchCount();
        return (int) result;
    }

    @Override
    public List<Grade> getClassGrade(int schoolCode, int curGrade, int homeClass) {
        //전체 성적 중 학기 코드가 현재 연도로 시작하는 것 / 과목코드가 현재 학년인 것 / 반별로 정의
        return queryFactory.selectFrom(grade)
                .where(grade.user.schoolCode.eq(schoolCode).and(grade.semesterCode.stringValue().startsWith(curYear))
                        .and(grade.subject.subjectCode.endsWith(String.valueOf(curGrade)))
                        .and(grade.user.homeClass.eq(homeClass)))
                .fetch();
    }

    @Override
    public List<Grade> getStudentGrade(long id) {
        //현재 학생의 아이디
        //그 학생의 현재 학년의 성적
        return queryFactory.selectFrom(grade)
                .where(grade.user.id.eq(id)
                        .and(grade.semesterCode.stringValue().startsWith(curYear)))
                .fetch();
    }

    @Override
    public User findUserByUserInfo(int schoolCode, int curGrade, int homeClass, String name) {
        return queryFactory.selectFrom(user).where(user.schoolCode.eq(schoolCode)
                .and(user.curGrade.eq(curGrade)).and(user.homeClass.eq(homeClass)).and(user.name.like(name)))
                .fetchOne();
    }

    @Override
    public List<Integer> getClassBySchoolCode(int schoolCode, int grade) {
        return queryFactory.select(user.homeClass).from(user)
                .where(user.schoolCode.eq(schoolCode).and(user.curGrade.eq(grade)))
                .distinct().fetch();
    }

    @Override
    public List<String> getStudentBySchoolCodeAndGrade(int schoolCode, int grade, int homeClass) {
        return queryFactory.select(user.name).from(user)
                .where(user.schoolCode.eq(schoolCode).and(user.curGrade.eq(grade)).and(user.homeClass.eq(homeClass)).and(user.positionChecker.eq(1)))
                .fetch();
    }

    @Override
    public List<Grade> getScoreByUser(User u, int semesterCode) {
        return queryFactory.selectFrom(grade)
                .where(grade.user.eq(u).and(grade.semesterCode.eq(semesterCode)))
                .fetch();
    }

    @Override
    public Subject findSubjectId(String subjectCode) {
        return queryFactory.selectFrom(subject).where(subject.subjectCode.like(subjectCode)).fetchOne();
    }

    @Override
    @Modifying
    @Transactional
    public void update(Grade g) {
        queryFactory.update(grade)
                .where(grade.semesterCode.eq(g.getSemesterCode())
                        .and(grade.user.eq(g.getUser()))
                        .and(grade.subject.eq(g.getSubject())))
                .set(grade.score, g.getScore())
                .execute();
    }

    @Override
    public Grade findByUserAndSubject(User user, String subjectCode, int semesterCode) {
        return queryFactory.selectFrom(grade)
                .where(grade.user.eq(user).and(grade.subject.subjectCode.eq(subjectCode)).and(grade.semesterCode.eq(semesterCode)))
                .fetchOne();
    }

    @Override
    public int getUserCountByGrade(int schoolCode, int curGrade) {
        return (int) queryFactory.selectFrom(user)
                .where(user.schoolCode.eq(schoolCode).and(user.curGrade.eq(curGrade)).and(user.positionChecker.eq(1)))
                .fetchCount();
    }

    @Override
    public List<Grade> getTotalGrade(int schoolCode, int curGrade) {
        return queryFactory.selectFrom(grade)
                .where(grade.user.schoolCode.eq(schoolCode)
                        .and(grade.user.curGrade.eq(curGrade))
                        .and(grade.semesterCode.stringValue().startsWith(curYear)))
                .fetch();
    }

    @Override
    public List<HashMap> getAvg(int schoolCode, int curGrade, int classCount) {
        List<HashMap> list = new ArrayList<>();
        for(int i=1; i<=classCount; i++){
            List<Long> userIds = queryFactory.select(user.id).from(user)
                    .where(user.curGrade.eq(curGrade).and(user.homeClass.eq(i))).fetch();
            for(Long j : userIds){
                List<Integer> prev = queryFactory.select(grade.score).from(grade)
                        .where(grade.semesterCode.stringValue().startsWith(curYear).and(grade.semesterCode.stringValue().endsWith("11")).and(grade.user.id.eq(j))).fetch();
                int prevScore = 0;
                for (Integer k : prev) { prevScore += k; }
                List<Integer> curScores = queryFactory.select(grade.score).from(grade)
                        .where(grade.semesterCode.stringValue().startsWith(curYear).and(grade.semesterCode.stringValue().endsWith("12").and(grade.user.id.eq(j)))).fetch();
                int curScore = 0;
                for(Integer k : curScores) { curScore += k; }
                HashMap<String, Integer> map = new HashMap<>();
                map.put("class", i);
                map.put("original", curScore / 9 - prevScore / 9);
                map.put("current", curScore / 9);
                list.add(map);
            }
        }
        return list;
    }

    @Override
    public List<Grade> getTotalGradeByUser(User u) {
        return queryFactory.selectFrom(grade).where(grade.user.eq(u)).fetch();
    }

    @Override
    public List<Mock> getMocks(User u, int semesterCode) {
        return queryFactory.selectFrom(mock)
                .where(mock.user.eq(u).and(mock.semester_code.eq(semesterCode)))
                .fetch();
    }


}
