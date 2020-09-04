package com.pickle.web.streaming;

import com.pickle.web.schedules.QSchedule;
import com.pickle.web.schedules.Schedule;
import com.pickle.web.subjects.QSubjectDetail;
import com.pickle.web.users.QUser;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

interface IStreamingRepository {
    public Tuple findSUserData(int userCode);
    public Tuple findSSchedule(String checker);
    public List<?> findSStudentList(String checker);
    public String findTSubject(int userCode);
    List<Tuple> findTSchedule();
    Integer findTStudentList(String tClassCode);
}

@Repository
public class StreamingRepositoryImpl extends QuerydslRepositorySupport implements IStreamingRepository {
    private final JPAQueryFactory queryFactory;
    public StreamingRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Schedule.class);
        this.queryFactory = queryFactory;
    }
    @Override
    public Tuple findSUserData(int userCode) {
        QUser qUser = QUser.user;
        return from(qUser).select(qUser.curGrade,qUser.homeClass).where(qUser.userCode.eq(userCode)).fetchOne();
    }
    @Override
    public Tuple findSSchedule(String checker) {
        QSchedule qSchedule = QSchedule.schedule;
        Calendar cal = Calendar.getInstance();
        StringPath week = null;
        switch (cal.get(Calendar.DAY_OF_WEEK)){
            case 2 :
                week = qSchedule.mon;
                break;
            case 3 :
                week =qSchedule.tue;
                break;
            case 4:
                week =qSchedule.wed;
                break;
            case 5:
                week = qSchedule.thu;
                break;
            case 6:
                week = qSchedule.fri;
                break;
        }
        int period = 0;
        switch (cal.get(Calendar.HOUR_OF_DAY)){
            case 9:
                period = 1;
                break;
            case 10:
                period = 2;
                break;
            case 11:
                period = 3;
                break;
            case 12:
                period = 4;
                break;
            case 14:
                period = 5;
                break;
            case 15:
                period = 6;
                break;
        }
        return from(qSchedule).select(qSchedule.mon,qSchedule.period).where(qSchedule.period.eq(4).and(qSchedule.checker.like(checker))).fetchOne();
    }


    @Override
    public List<Tuple> findSStudentList(String checker) {
        QUser qUser = QUser.user;
        List<Tuple> userData = null;
        if (checker.charAt(1)=='1'){
            userData = from(qUser).select(qUser.userCode, qUser.name).where(qUser.curGrade.like(String.valueOf(checker.charAt(0))).and(qUser.homeClass.eq(1))).fetch();
        }else if (checker.charAt(1)=='2'){
            userData = from(qUser).select(qUser.userCode, qUser.name).where(qUser.curGrade.like(String.valueOf(checker.charAt(0))).and(qUser.homeClass.eq(8))).fetch();
        }
        return userData;
    }
    @Override
    public String findTSubject(int userCode) {
        QSubjectDetail qSubjectDetail = QSubjectDetail.subjectDetail;
        QUser qUser = QUser.user;
        return  from(qSubjectDetail).join(qSubjectDetail.user, qUser).select(qSubjectDetail.subject.subjectCode).where(qUser.userCode.eq(userCode)).fetch().get(0);
    }

    @Override
    public List<Tuple> findTSchedule() {
        QSchedule qSchedule = QSchedule.schedule;
        Calendar cal = Calendar.getInstance();
        StringPath week = null;
        switch (cal.get(Calendar.DAY_OF_WEEK)){
            case 2 :
                week = qSchedule.mon;
                break;
            case 3 :
                week =qSchedule.tue;
                break;
            case 4:
                week =qSchedule.wed;
                break;
            case 5:
                week = qSchedule.thu;
                break;
            case 6:
                week = qSchedule.fri;
                break;
        }
        int period = 0;
        switch (cal.get(Calendar.HOUR_OF_DAY)){
            case 9:
                period = 1;
                break;
            case 10:
                period = 2;
                break;
            case 11:
                period = 3;
                break;
            case 12:
                period = 4;
                break;
            case 14:
                period = 5;
                break;
            case 15:
                period = 6;
                break;
        }
        return from(qSchedule).select(qSchedule.mon,qSchedule.period).where(qSchedule.period.eq(4)).fetch();
    }

    @Override
    public Integer findTStudentList(String tClassCode) {
        String subCode = tClassCode.substring(0,5);
        String period = tClassCode.substring(5,6);
        QSchedule qSchedule = QSchedule.schedule;
        return from(qSchedule).select(qSchedule.checker).where(qSchedule.period.eq(Integer.valueOf(period)).and((qSchedule.mon.eq(subCode)).or(qSchedule.tue.eq(subCode)).or(qSchedule.wed.eq(subCode)).or(qSchedule.thu.eq(subCode)).or(qSchedule.fri.eq(subCode)))).fetchOne();
    }
}
