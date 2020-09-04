package com.pickle.web.chatbot;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class ChatbotController {
    @Autowired ChatbotServiceImpl chatbotService;
    @PostMapping("/chatbot")
    public Map<String, Object> chatbot(@RequestBody Map<String, Object> params) { return chatbotService.func(params); }
}