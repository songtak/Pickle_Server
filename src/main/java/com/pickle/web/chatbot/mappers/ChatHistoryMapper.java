package com.pickle.web.chatbot.mappers;

import com.pickle.web.chatbot.ChatHistory;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatHistoryMapper {
    public void insertData(ChatHistory chatHistory);
}