package com.pickle.web.notices;

import static com.pickle.web.notices.QNoticeComment.noticeComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

interface ICommentRepository {

    @Query(value = "insert into notice_comment (id, article_no, comment_contents) values (:id, :articleNo, :commentContents)", nativeQuery = true)
    @Modifying @Transactional
    void insert(@Param("id")Long id, @Param("articleNo")Long articleNo, @Param("commentContents")String commentContents);
    List<NoticeComment> findAllComment(Long articleNo);
    NoticeComment findOneComment(long articleNo);
    int countByArticleNo(long articleNo);
    void deleteByCommentNo(long commentNo);
}
@Repository
public class CommentRepositoryImpl extends QuerydslRepositorySupport implements ICommentRepository {
    private final JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(JPAQueryFactory queryFactory) {
        super(NoticeComment.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<NoticeComment> findAllComment(Long articleNo) {
        return queryFactory.selectFrom(noticeComment).where(noticeComment.notice.id.eq(articleNo)).fetch();
    }

    @Override
    public void insert(Long id, Long articleNo, String commentContents) {

    }

    @Override
    public NoticeComment findOneComment(long articleNo) {
        return queryFactory.selectFrom(noticeComment).where(noticeComment.id.eq(articleNo)).fetchOne();
    }

    @Override
    public int countByArticleNo(long articleNo) {
        return (int) queryFactory.selectFrom(noticeComment).where(noticeComment.notice.id.eq(articleNo)).fetchCount();
    }

    @Override @Modifying @Transactional
    public void deleteByCommentNo(long commentNo) {
        queryFactory.delete(noticeComment).where(noticeComment.id.eq(commentNo)).execute();
    }

}
