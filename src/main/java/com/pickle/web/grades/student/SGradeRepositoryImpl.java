package com.pickle.web.grades.student;


import com.pickle.web.grades.Grade;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;


interface ISGradeRepository{
}



@Repository
public class SGradeRepositoryImpl extends QuerydslRepositorySupport implements ISGradeRepository{
    private final JPAQueryFactory queryFactory;
    public SGradeRepositoryImpl(JPAQueryFactory queryFactory){
        super(Grade.class);
        this.queryFactory = queryFactory;
    }


}
