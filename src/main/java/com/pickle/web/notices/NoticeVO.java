package com.pickle.web.notices;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticeVO {
    private Long id;
    private String title;
    private String category;
    private String password;
    private String contents;
    private LocalDateTime createDate;
    private Long makerId;
    private String makerName;
    private int commentCount;
}
