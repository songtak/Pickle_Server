package com.pickle.web.grades;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

interface IMockRepository{

}
@Repository
public class MockRepositoryImpl extends QuerydslRepositorySupport implements IMockRepository{
    private final JPAQueryFactory queryFactory;
    public MockRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Mock.class);
        this.queryFactory = queryFactory;
    }
}
