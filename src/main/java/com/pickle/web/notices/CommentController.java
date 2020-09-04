package com.pickle.web.notices;

import com.pickle.web.users.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping("/noticecomment")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save")
    public void insert(@RequestBody HashMap<?, ?> comment){
        commentService.insert(comment);
    }
    @GetMapping("/delete/{commentNo}")
    public void delete(@PathVariable String commentNo){
        commentService.delete(commentNo);
    }
}