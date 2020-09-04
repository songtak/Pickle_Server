package com.pickle.web.attendances.student;

import static com.pickle.web.users.QUser.user;
import static com.pickle.web.attendances.QAttendance.attendance;
import com.pickle.web.attendances.Attendance;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

interface ISAttendanceRepository {
    int getTodayClassAbsent(int curGrade, int homeClass);

    int getPresentCountById(long id);

    int getTotalDayById(long id);
}
@Repository
public class SAttendanceRepositoryImpl extends QuerydslRepositorySupport implements ISAttendanceRepository{
    private final JPAQueryFactory queryFactory;
    public SAttendanceRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Attendance.class);
        this.queryFactory = queryFactory;
    }
    @Override
    public int getTodayClassAbsent(int curGrade, int homeClass) {
        //String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String today = "2018-03-05";
        return (int) queryFactory.selectFrom(attendance)
                .where(attendance.date.stringValue().startsWith(today)
                        .and(attendance.absent.eq(1))
                        .and(attendance.user.curGrade.eq(curGrade))
                        .and(attendance.user.homeClass.eq(homeClass)))
                .fetchCount();
    }

    @Override
    public int getPresentCountById(long id) {
        return (int) queryFactory.selectFrom(attendance)
                .where(attendance.user.id.eq(id)
                        .and(attendance.present.eq(1)))
                .fetchCount();
    }

    @Override
    public int getTotalDayById(long id) {
        return (int) queryFactory.selectFrom(attendance)
                .where(attendance.user.id.eq(id))
                .fetchCount();
    }
}
