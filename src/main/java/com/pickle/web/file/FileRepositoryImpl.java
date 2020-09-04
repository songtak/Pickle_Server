package com.pickle.web.file;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

interface IFileRepository {

}
@Repository
public class FileRepositoryImpl extends QuerydslRepositorySupport implements IFileRepository {
    private final JPAQueryFactory queryFactory;

    public FileRepositoryImpl(JPAQueryFactory queryFactory) {
        super(File.class);
        this.queryFactory = queryFactory;
    }


}
