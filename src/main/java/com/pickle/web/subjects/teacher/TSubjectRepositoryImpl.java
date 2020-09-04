package com.pickle.web.subjects.teacher;

import static com.pickle.web.users.QUser.user;
import static com.pickle.web.subjects.QSubjectDetail.subjectDetail;
import static com.pickle.web.subjects.QSubject.subject;

import com.pickle.web.subjects.Subject;
import com.pickle.web.subjects.SubjectDetail;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

interface ISubjectRepository {
    List<SubjectDetail> getDetailList(int userCode);
    void deleteOne(int userCode, int lessonId);
    void updateOne2(SubjectDetailVO detail, int detailId);

    String findSubjectIdByUserCode(int userCode);
    String getUserNameByUserCode(int userCode);
    String getSubjectNameByUserCode(int userCode);

    void updateList(Map<String, String> updatedDetail);
    String getSubjectNameBySubjectCode(String subjectCode);
    String getSubjectCodeByUserCode(int userCode);
}
@Repository
public class TSubjectRepositoryImpl extends QuerydslRepositorySupport implements ISubjectRepository{
    private final JPAQueryFactory qf;


    public TSubjectRepositoryImpl(JPAQueryFactory qf) {
        super(SubjectDetail.class);
        this.qf = qf;
    }

    @Override
    public List<SubjectDetail> getDetailList(int userCode) {
        return qf.selectFrom(subjectDetail)
                .where(subjectDetail.user.userCode.eq(userCode))
                .orderBy(subjectDetail.lessonNo.asc())
                .fetch();
    }

//    @Override
//    public void updateOne(int userCode, int lessonNo, SubjectDetail updatedDetail) {
//        qf.update(subjectDetail)
//            .where(subjectDetail.user.userCode.eq(userCode)
//            .and(subjectDetail.lessonNo.eq(lessonNo)))
//            .set(subjectDetail.lessonTitle,updatedDetail.getLessonTitle())
//            .set(subjectDetail.lessonDetail,updatedDetail.getLessonDetail())
//            .execute();
//    }

    //confirmed
    @Override
    public void deleteOne(int userCode, int lessonId) {
        qf.update(subjectDetail)
                .where(subjectDetail.id.eq((long) lessonId))
                .set(subjectDetail.lessonTitle," ")
                .set(subjectDetail.lessonDetail," ")
                .execute();
    }

    @Override
    public void updateOne2(SubjectDetailVO detail, int detailId) {
        qf.update(subjectDetail)
                .where(subjectDetail.id.eq((long) detailId))
                .set(subjectDetail.lessonNo,detail.getLessonNo())
                .set(subjectDetail.lessonTitle,detail.getLessonTitle())
                .set(subjectDetail.lessonDetail,detail.getLessonDetail())
                .execute();
    }

    @Override
    public String findSubjectIdByUserCode(int userCode) {
       return qf.select(subjectDetail.subject.subjectCode).from(subjectDetail)
               .where(subjectDetail.user.userCode.eq(userCode))
               .distinct().fetchOne();
    }

    @Override
    public String getSubjectNameByUserCode(int userCode) {
        return qf.select(subjectDetail.subject.subjectName)
                .from(subjectDetail)
                .where(subjectDetail.user.userCode.eq(userCode)).distinct()
                .fetchOne();
    }

    @Override
    public void updateList(Map<String, String> updatedDetail) {
        qf.update(subjectDetail)
                .where(subjectDetail.id.eq(Long.valueOf(updatedDetail.get("id"))))
                .set(subjectDetail.lessonNo, Integer.parseInt(updatedDetail.get("lessonNo")))
                .set(subjectDetail.lessonTitle,updatedDetail.get("lessonTitle"))
                .set(subjectDetail.lessonDetail,updatedDetail.get("lessonDetail"))
                .execute();
    }

    @Override
    public String getSubjectNameBySubjectCode(String subjectCode) {
        return qf.select(subject.subjectName)
                .from(subject)
                .where(subject.subjectCode.like(subjectCode))
                .fetchOne();
    }

    @Override
    public String getSubjectCodeByUserCode(int userCode) {
        return qf.select(subjectDetail.subject.subjectCode)
                .from(subjectDetail)
                .where(subjectDetail.user.userCode.eq(userCode)).distinct()
                .fetchOne();
    }

    @Override
    public String getUserNameByUserCode (int userCode) {
        return qf.select(subjectDetail.user.name)
                .from(subjectDetail)
                .where(subjectDetail.user.userCode.eq(userCode)).distinct()
                .fetchOne();

    }
}
