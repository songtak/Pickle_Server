package com.pickle.web.users;

import static com.pickle.web.users.QUser.user;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

interface IUserRepository {
    User findUser(User user);
    void update(User user);
}

@Repository
public class UserRepositoryImpl extends QuerydslRepositorySupport implements IUserRepository {
    private final JPAQueryFactory queryFactory;
    UserRepositoryImpl(JPAQueryFactory queryFactory) {
        super(User.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public User findUser(User u) {
        return queryFactory
                .selectFrom(user)
                .where(user.userId.eq(u.getUserId())
                        .and(user.userPw.eq(u.getUserPw()))
                        .and(user.positionChecker.eq(u.getPositionChecker())))
                .fetchOne();
    }

    @Override
    public void update(User u) {
        //schoolName, curGrade, homeClass, phone, email
        queryFactory
                .update(user)
                .where(user.userCode.eq(u.getUserCode()).and(user.userId.eq(u.getUserId())))
                .set(user.schoolCode, u.getSchoolCode())
                .set(user.schoolName, u.getSchoolName())
                .set(user.curGrade, u.getCurGrade())
                .set(user.homeClass, u.getHomeClass())
                .set(user.phone, u.getPhone())
                .set(user.email, u.getEmail())
                .execute();
    }

}
