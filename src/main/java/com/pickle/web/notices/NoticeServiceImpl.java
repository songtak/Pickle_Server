package com.pickle.web.notices;

import com.pickle.web.commons.JpaService;
import com.pickle.web.commons.Pagination;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

interface NoticeService extends JpaService<Notice>{
    void update(Notice notice);
    List<NoticeVO> pagingList(String selectedCate, Pagination pagination);
    Notice insert(HashMap<?, ?> notice);
    NoticeVO getNoticeVOById(long articleNo);
    List<String> getCategory();
    int countByCategory(String selectedCate);
    List<NoticeVO> getTwo();
}
@Service
public class NoticeServiceImpl implements NoticeService{
    private final NoticeRepository noticeRepository;
    private final CommentRepository commentRepository;

    public NoticeServiceImpl(NoticeRepository noticeRepository, CommentRepository commentRepository) {
        this.noticeRepository = noticeRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Optional<Notice> findById(String id) {
        return noticeRepository.findById(Long.valueOf(id));
    }

    @Override
    public Iterable<Notice> findAll() {
        return noticeRepository.findAll();
    }

    @Override
    public int count() {
        return (int) noticeRepository.count();
    }

    @Override
    public void delete(String id) {
        noticeRepository.delete(findById(id).orElse(new Notice()));
    }

    @Override
    public boolean exists(String id) {
        return noticeRepository.existsById(Long.valueOf(id));
    }

    @Override
    public void update(Notice notice) {
        noticeRepository.update(notice);
    }

    @Override
    public List<NoticeVO> pagingList(String selectedCate, Pagination pagination) {
        List<NoticeVO> result = new ArrayList<>();
        List<Notice> list = noticeRepository.pagingList(selectedCate, pagination);
        NoticeVO vo = null;
        for(Notice i : list){
            vo = converToNoticeVO(i);
            vo.setCommentCount(commentRepository.countByArticleNo(i.getId()));
            result.add(vo);
        }
        return result;
    }

    @Override
    public Notice insert(HashMap<?, ?> notice) {
        Long id = Long.parseLong((String) notice.get("userTableId"));
        String title = (String)notice.get("title");
        if(title == "") title = "제목없음";
        String password = (String)notice.get("password");
        String contents = (String)notice.get("contents");
        String category = (String)notice.get("category");
        String createDate = new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date());
        noticeRepository.insert(id, title, password, contents, category, createDate);
        return noticeRepository.getRecentOne();
    }

    @Override
    public NoticeVO getNoticeVOById(long articleNo) {
        return converToNoticeVO(noticeRepository.findById(articleNo).get());
    }

    @Override
    public List<String> getCategory() {
        return noticeRepository.getCategory()
                .stream().distinct().collect(Collectors.toList());
    }

    @Override
    public int countByCategory(String selectedCate) {
        return noticeRepository.countByCategory(selectedCate);
    }

    @Override
    public List<NoticeVO> getTwo() {
        NoticeVO vo = null;
        List<NoticeVO> list = new ArrayList<>();
        List<Notice> origin = noticeRepository.getTwo();
        for(Notice i : origin){
            vo = converToNoticeVO(i);
            list.add(vo);
        }
        return list;
    }
    public NoticeVO converToNoticeVO(Notice n){
        NoticeVO vo = new NoticeVO();
        vo.setId(n.getId());
        vo.setTitle(n.getTitle());
        vo.setCategory(n.getCategory());
        vo.setPassword(n.getPassword());
        vo.setContents(n.getContents());
        vo.setCreateDate(n.getCreateDate());
        vo.setMakerId(n.getUser().getId());
        vo.setMakerName(n.getUser().getName());
        return vo;
    }

}