package com.pickle.web.attendances.teacher;

import static com.pickle.web.users.QUser.user;
import static com.pickle.web.attendances.QAttendance.attendance;

import com.pickle.web.attendances.Attendance;
import com.pickle.web.users.User;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

interface IAttendanceRepository {

    int getPresentCount(LocalDate localDate,int grade, int homeClass);
    int getAbsentCount(LocalDate localDate,int grade, int homeClass);
    int getTardyCount(LocalDate localDate,int grade, int homeClass);
    void updatePresent(int userCode, LocalDate localDate);
    void updateAbsent(int userCode, LocalDate localDate);
    List<Integer> checkAbsent(int userCode, LocalDate localDate);
    void updateTardy(int userCode, LocalDate localDate);
    List<User> getGroup(int curGrade, int homeClass);
    int getGroupCount(int curGrade, int homeClass);
    int getMonthlyAbsentCount(int userCode, LocalDate startDate, LocalDate endDate);
}

@Repository
public class TAttendanceRepositoryImpl extends QuerydslRepositorySupport implements IAttendanceRepository {

    private final JPAQueryFactory qf;

    public TAttendanceRepositoryImpl(JPAQueryFactory qf) {
        super(Attendance.class);
        this.qf = qf;
    }
    @Override
    public int getPresentCount(LocalDate localDate,int grade, int homeClass) {
        return (int) qf.from(attendance)
                .select(attendance.present)
                .where(attendance.date.eq(localDate)
                .and(attendance.user.curGrade.eq(grade))
                .and(attendance.user.homeClass.eq(homeClass))
                .and(attendance.present.eq(1)))
                .fetchCount();
    }

    @Override
    public int getAbsentCount(LocalDate localDate,int grade, int homeClass) {
        return (int) qf.from(attendance)
                .select(attendance.absent)
                .where(attendance.date.eq(localDate)
                .and(attendance.user.curGrade.eq(grade))
                .and(attendance.user.homeClass.eq(homeClass))
                .and(attendance.absent.eq(1)))
                .fetchCount();
    }

    @Override
    public int getTardyCount(LocalDate localDate,int grade, int homeClass) {
        return (int) qf.from(attendance)
                .select(attendance.tardy)
                .where(attendance.date.eq(localDate)
                .and(attendance.user.curGrade.eq(grade))
                .and(attendance.user.homeClass.eq(homeClass))
                .and(attendance.tardy.eq(1)))
                .fetchCount();
    }

    @Override
    public void updatePresent(int userCode, LocalDate localDate) {
        qf.update(attendance)
            .where(attendance.date.eq(localDate).and(attendance.user.userCode.eq(userCode)))
                .set(attendance.present,1)
                .set(attendance.absent,0)
                .set(attendance.tardy,0)
                .execute();
    }
    @Override
    public void updateAbsent(int userCode, LocalDate localDate) {
        qf.update(attendance)
                .where(attendance.date.eq(localDate).and(attendance.user.userCode.eq(userCode)))
                .set(attendance.present,0)
                .set(attendance.absent,1)
                .set(attendance.tardy,0)
                .execute();
    }

    @Override
    public List<Integer> checkAbsent(int userCode, LocalDate localDate) {
        return qf.select(attendance.absent).from(attendance)
                .where(attendance.date.eq(localDate).and(attendance.user.userCode.eq(userCode)))
                .fetch();
    }

    @Override
    public void updateTardy(int userCode, LocalDate localDate) {
        qf.update(attendance)
                .where(attendance.date.eq(localDate).and(attendance.user.userCode.eq(userCode)))
                .set(attendance.present,0)
                .set(attendance.absent,0)
                .set(attendance.tardy,1)
                .execute();
    }

    @Override
    public List<User> getGroup(int curGrade, int homeClass) {
        return qf.selectFrom(user)
                .where(user.positionChecker.eq(1)
                .and(user.curGrade.eq(curGrade))
                .and(user.homeClass.eq(homeClass))).fetch();
    }

    @Override
    public int getGroupCount(int curGrade, int homeClass) {
        return (int) qf.selectFrom(user)
                .where(user.positionChecker.eq(1)
                .and(user.curGrade.eq(curGrade))
                .and(user.homeClass.eq(homeClass))).fetchCount();
    }

    @Override
    public int getMonthlyAbsentCount(int userCode,LocalDate fromDate,LocalDate toDate) {
        return (int) qf.selectFrom(attendance)
                .where(attendance.user.userCode.eq(userCode)
                .and(attendance.date.between(fromDate,toDate)
                .and(attendance.absent.eq(1))))
                .fetchCount();
    }
}
