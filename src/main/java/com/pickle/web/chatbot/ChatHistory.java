package com.pickle.web.chatbot;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component @Lazy
@Data @Alias("chatHistory")
@MappedTypes(LocalDate.class)
public class ChatHistory {
    private int id, chatId;
    private String userInfo, chatKind, chatBody;
    private LocalDateTime insertDate, updateDate;
}