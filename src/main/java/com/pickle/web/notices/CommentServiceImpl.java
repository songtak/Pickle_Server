package com.pickle.web.notices;

import com.pickle.web.commons.JpaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

interface CommentService extends JpaService<NoticeComment> {

    List<NoticeComment> findAllComment(String articleNo);
    List<String> findCommentMaker(String articleNo);
    void insert(HashMap<?,?> comment);
    NoticeComment findOneComment(String id);
    List<NoticeCommentVO> getAllCommentVOById(long articleNo);
}
@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final NoticeRepository noticeRepository;

    public CommentServiceImpl(CommentRepository commentRepository, NoticeRepository noticeRepository) {
        this.commentRepository = commentRepository;
        this.noticeRepository = noticeRepository;
    }

    @Override
    public Optional<NoticeComment> findById(String id) {
        return commentRepository.findById(Long.valueOf(id));
    }

    @Override
    public Iterable<NoticeComment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public int count() {
        return (int) commentRepository.count();
    }

    @Override
    public void delete(String id) {
        commentRepository.deleteByCommentNo(Long.parseLong(id));
    }

    @Override
    public boolean exists(String id) {
        return commentRepository.existsById(Long.valueOf(id));
    }

    @Override
    public List<NoticeComment> findAllComment(String articleNo) {
        return commentRepository.findAllComment(Long.valueOf(articleNo));
    }

    @Override
    public List<String> findCommentMaker(String articleNo) {
        return null;
    }

    @Override
    public void insert(HashMap<?, ?> comment) {
        Long id = Long.parseLong((String) comment.get("userTableId"));
        Long articleNo = Long.parseLong((String) comment.get("articleNo"));
        String commentContents = (String) comment.get("commentContents");
        commentRepository.insert(id, articleNo, commentContents);
    }

    @Override
    public NoticeComment findOneComment(String id) {
        return commentRepository.findOneComment(Long.parseLong(id));
    }

    @Override
    public List<NoticeCommentVO> getAllCommentVOById(long articleNo) {
        List<NoticeCommentVO> returnList = new ArrayList<>();
        List<NoticeComment> list = commentRepository.findAllComment(articleNo);
        NoticeCommentVO ncv;
        for(NoticeComment nc : list){
            ncv = new NoticeCommentVO();
            ncv.setId(nc.getId());
            ncv.setCommentContents(nc.getCommentContents());
            ncv.setMakerId(nc.getUser().getId());
            ncv.setMakerName(nc.getUser().getName());
            returnList.add(ncv);
        }
        return returnList;
    }

}