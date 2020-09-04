package com.pickle.web.schedules;

import static com.pickle.web.schedules.QSchedule.schedule;
import com.pickle.web.commons.Box;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

interface IScheduleRepository {
    Long findId(Schedule item);
    List<Schedule> teacherTimetable(int curGrade);
    List<Schedule> studentTimetable(int checker);
    void updateMonCube(int checker, int i, String subjectCode);
    void updateTueCube(int checker, int i, String subjectCode);
    void updateWedCube(int checker, int i, String subjectCode);
    void updateThuCube(int checker, int i, String subjectCode);
    void updateFriCube(int checker, int i, String subjectCode);

    void updateMOne(int checker, int period, String subjectCode);
    void updateTOne(int checker, int period, String subjectCode);
    void updateWOne(int checker, int period, String subjectCode);
    void updateThOne(int checker, int period, String subjectCode);
    void updateFOne(int checker, int period, String subjectCode);
}

@Repository
public class ScheduleRepositoryImpl extends QuerydslRepositorySupport implements IScheduleRepository {
    private final JPAQueryFactory qf;

    public ScheduleRepositoryImpl(JPAQueryFactory qf) {
        super(Schedule.class);
        this.qf = qf;
    }

    @Override
    public List<Schedule> teacherTimetable(int curGrade) {
        return qf.selectFrom(schedule)
                .where(schedule.checker.between(curGrade*10,curGrade*10+10))
                .fetch();
    }

    @Override
    public List<Schedule> studentTimetable(int checker) {
        return qf.selectFrom(schedule)
                .where(schedule.checker.eq(checker)).fetch();
    }

    @Override
    public Long findId(Schedule item) {
        return null;
    }

    @Override
    public void updateMonCube(int checker, int i, String subjectCode) {
        qf.update(schedule)
                .where(schedule.checker.eq(checker)
                .and(schedule.period.eq(i)))
                .set(schedule.mon,subjectCode).execute();

    }
    @Override
    public void updateTueCube(int checker, int i, String subjectCode) {
        qf.update(schedule)
                .where(schedule.checker.eq(checker)
                        .and(schedule.period.eq(i)))
                .set(schedule.tue,subjectCode).execute();

    }
    @Override
    public void updateWedCube(int checker, int i, String subjectCode) {
        qf.update(schedule)
                .where(schedule.checker.eq(checker)
                        .and(schedule.period.eq(i)))
                .set(schedule.wed,subjectCode)
                .execute();

    }
    @Override
    public void updateThuCube(int checker, int i, String subjectCode) {
        qf.update(schedule)
                .where(schedule.checker.eq(checker)
                        .and(schedule.period.eq(i)))
                .set(schedule.thu,subjectCode).execute();
    }
    @Override
    public void updateFriCube(int checker, int i, String subjectCode) {
        qf.update(schedule)
                .where(schedule.checker.eq(checker)
                        .and(schedule.period.eq(i)))
                .set(schedule.fri,subjectCode).execute();

    }

    @Override
    public void updateMOne(int checker, int period, String subjectCode) {
        qf.update(schedule)
                .where(schedule.checker.eq(checker)
                        .and(schedule.period.eq(period)))
                        .set(schedule.mon, subjectCode)
                        .execute();
    }

    @Override
    public void updateTOne(int checker, int period, String subjectCode) {
        qf.update(schedule)
                .where(schedule.checker.eq(checker)
                        .and(schedule.period.eq(period)))
                .set(schedule.tue,subjectCode)
                .execute();
    }

    @Override
    public void updateWOne(int checker, int period, String subjectCode) {
        qf.update(schedule)
                .where(schedule.checker.eq(checker)
                        .and(schedule.period.eq(period)))
                .set(schedule.wed,subjectCode)
                .execute();
    }

    @Override
    public void updateThOne(int checker, int period, String subjectCode) {
        qf.update(schedule)
                .where(schedule.checker.eq(checker)
                        .and(schedule.period.eq(period)))
                .set(schedule.thu,subjectCode)
                .execute();
    }

    @Override
    public void updateFOne(int checker, int period, String subjectCode) {
        qf.update(schedule)
                .where(schedule.checker.eq(checker)
                        .and(schedule.period.eq(period)))
                .set(schedule.fri,subjectCode)
                .execute();
    }

}
