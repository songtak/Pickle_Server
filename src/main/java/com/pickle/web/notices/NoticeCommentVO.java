package com.pickle.web.notices;

import lombok.Data;

@Data
public class NoticeCommentVO {
    private Long id;
    private String commentContents;
    private Long makerId;
    private String makerName;
}
