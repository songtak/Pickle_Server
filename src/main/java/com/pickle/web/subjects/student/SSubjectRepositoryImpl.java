package com.pickle.web.subjects.student;

import com.pickle.web.subjects.Subject;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;


interface ISSubjectRepository{

}


@Repository
public class SSubjectRepositoryImpl extends QuerydslRepositorySupport implements ISSubjectRepository{
    private final JPAQueryFactory queryFactory;

    public SSubjectRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Subject.class);
        this.queryFactory = queryFactory;
    }
}
