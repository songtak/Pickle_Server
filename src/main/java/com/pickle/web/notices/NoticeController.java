package com.pickle.web.notices;

import com.pickle.web.commons.Box;
import com.pickle.web.file.File;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import com.pickle.web.commons.Pagination;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping("/notice")
@AllArgsConstructor
public class NoticeController {
    @Autowired Pagination pagination;
    @Autowired Box box;
    private final NoticeService noticeService;
    private final CommentService commentService;
    private final NoticeRepository noticeRepository;

    @PostMapping("/save")
    public Notice insert(@RequestBody HashMap<?, ?> notice){
        return noticeService.insert(notice);
    }
    @PostMapping("/update")
    public void update(@RequestBody Notice notice){ noticeService.update(notice); }
    @GetMapping("/delete/{articleNo}")
    public void delete(@PathVariable String articleNo){ noticeService.delete(articleNo); }
    @GetMapping("/list/{selectedCate}/{currentPage}")
    public HashMap<String, ?> pageList(@PathVariable("selectedCate") String selectedCate, @PathVariable("currentPage") String currentPage){
        if(!selectedCate.equals("all")) {
            pagination.paging(noticeService.countByCategory(selectedCate), Integer.parseInt(currentPage));
        }else {
            pagination.paging(noticeService.count(), Integer.parseInt(currentPage));
        }
        List<NoticeVO> list = noticeService.pagingList(selectedCate, pagination);
        box.clear();
        box.put("pagination", pagination);
        box.put("list", list);
        return box.get();
    }
    @GetMapping("/detail/{articleNo}") @Transactional
    public HashMap<String, ?> detail(@PathVariable String articleNo){
        box.clear();
        NoticeVO noticeVO = noticeService.getNoticeVOById(Long.parseLong(articleNo));
        List<NoticeCommentVO> noticeCommentVO = commentService.getAllCommentVOById(Long.parseLong(articleNo));
        List<File> files = noticeRepository.getFilesByArticleNo(Long.parseLong(articleNo));
        box.put("notice", noticeVO);
        box.put("noticeComment", noticeCommentVO);
        box.put("files", files);
        return box.get();
    }
    @GetMapping("/getCategory")
    public List<String> getCategory(){
        return noticeService.getCategory();
    }
    @GetMapping("/mainPage")
    public List<NoticeVO> mainPage() {return noticeService.getTwo();}
}