package com.pickle.web.notices;

import com.pickle.web.commons.Pagination;
import com.pickle.web.file.File;
import com.pickle.web.subjects.QSubject;
import com.querydsl.core.BooleanBuilder;
import static com.pickle.web.notices.QNotice.notice;
import static com.pickle.web.file.QFile.file;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

interface INoticeRepository {

    void update(Notice no);
    List<Notice> pagingList(String selectedCate, Pagination pagination);
    @Modifying @Transactional
    @Query(value = "insert into notice (title, category, password, contents, create_date, id) values (:title, :category, :password, :contents, :createDate, :id)", nativeQuery = true)
    void insert(@Param("id")Long id,
                 @Param("title")String title,
                 @Param("password")String password,
                 @Param("contents")String contents,
                 @Param("category")String category,
                 @Param("createDate")String createDate);
    List<String> getCategory();

    List<Notice> getTwo();

    Notice getRecentOne();

    List<File> getFilesByArticleNo(long articleNo);
}
@Repository
public class NoticeRepositoryImpl extends QuerydslRepositorySupport implements INoticeRepository {
    private final JPAQueryFactory queryFactory;

    public NoticeRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Notice.class);
        this.queryFactory = queryFactory;
    }

    @Override @Modifying @Transactional
    public void update(Notice no) {
        queryFactory.update(notice)
                .where(notice.id.eq(no.getId()))
                .set(notice.title, no.getTitle())
                .set(notice.password, no.getPassword())
                .set(notice.contents, no.getContents())
                .set(notice.category, no.getCategory())
                .execute();
    }

    @Override
    public List<Notice> pagingList(String selectedCate, Pagination pagination) {
        BooleanBuilder builder = null;
        if(!selectedCate.equals("all")) {
            builder = new BooleanBuilder();
            builder.and(notice.category.like(selectedCate));
        }
        return queryFactory.selectFrom(notice)
                .where(builder)
                .orderBy(notice.id.desc())
                .offset(pagination.getStartRow())
                .limit(pagination.getPageSize())
                .fetch();
    }

    @Override
    public void insert(Long id, String title, String password, String contents, String category, String createDate) {

    }

    @Override
    public List<String> getCategory() {
        QSubject qs = QSubject.subject;
        return queryFactory.select(qs.subjectName).from(qs).fetch();

    }

    @Override
    public List<Notice> getTwo() {
        return queryFactory.selectFrom(notice).where(notice.category.eq("전체 공지")).orderBy(notice.id.desc()).limit(2).fetch();
    }

    @Override
    public Notice getRecentOne() {
        return queryFactory.selectFrom(notice).orderBy(notice.id.desc()).limit(1).fetchOne();
    }

    @Override
    public List<File> getFilesByArticleNo(long articleNo) {
        return queryFactory.selectFrom(file).where(file.notice.id.eq(articleNo)).fetch();
    }


}