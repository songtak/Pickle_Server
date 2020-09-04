package com.pickle.web.chatbot.mappers;

import com.pickle.web.chatbot.Chat;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMapper {
    public void insertData(Chat chat);
    public Chat selectUserList(int chat);
}