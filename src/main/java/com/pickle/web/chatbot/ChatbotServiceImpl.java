package com.pickle.web.chatbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.pickle.web.chatbot.mappers.ChatHistoryMapper;
import com.pickle.web.chatbot.mappers.ChatMapper;
import com.pickle.web.chatbot.mappers.ExamAnalysisMapper;
import com.pickle.web.chatbot.mappers.ExamMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class ChatbotServiceImpl {
    @Autowired Chat chat;
    @Autowired ChatHistory chatHistory;
    @Autowired Exam exam;
    @Autowired ExamAnalysis examAnalysis;
    @Autowired ChatMapper chatMapper;
    @Autowired ChatHistoryMapper historyMapper;
    @Autowired ExamMapper examMapper;
    @Autowired ExamAnalysisMapper analysisMapper;
    @Autowired RedisTemplate<String, Object> tem;
    private static ObjectMapper mapper = new JsonMapper();
    private JSONParser parser = new JSONParser();
    private JSONObject obj, obj0, obj1, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9, arrObj;
    private JSONArray arr, arr0, arr1, arr2;
    private ValueOperations<String, Object> vo;
    private String jStr, text, title, title1, url, url1, img, img1, body, ch, info;

    public Map<String, Object> json(String div, String button) {
        obj = new JSONObject();
        obj0 = new JSONObject();
        obj1 = new JSONObject();
        obj2 = new JSONObject();
        obj3 = new JSONObject();
        obj4 = new JSONObject();
        obj5 = new JSONObject();
        obj6 = new JSONObject();
        obj7 = new JSONObject();
        obj8 = new JSONObject();
        obj9 = new JSONObject();
        arrObj = new JSONObject();
        arr = new JSONArray();
        arr0 = new JSONArray();
        arr1 = new JSONArray();
        arr2 = new JSONArray();
        vo = tem.opsForValue();
        if (div.equals("sim")) {
            obj2.put("text", vo.get("text"));
            obj1.put("simpleText", obj2);
            if (button.equals("sBut")) {
                obj5.put("message", vo.get("tm"));
                obj5.put("label", vo.get("tm"));
                obj5.put("action", "message");
                arr0.add(obj5);
                obj4.put("message", vo.get("sm"));
                obj4.put("label", vo.get("sm"));
                obj4.put("action", "message");
                arr0.add(obj4);
                obj3.put("message", vo.get("fm"));
                obj3.put("label", vo.get("fm"));
                obj3.put("action", "message");
                arr0.add(obj3);
            }
        } else if (div.equals("bas")) {
            obj3.put("imageUrl", vo.get("img"));
            obj2.put("thumbnail", obj3);
            obj2.put("description", vo.get("des"));
            obj2.put("title", vo.get("title"));
            obj1.put("basicCard", obj2);
            if (button.equals("bBut")) {
                obj4.put("action", "message");
                obj4.put("label", vo.get("fm"));
                obj4.put("webLinkUrl", vo.get("fUrl"));
                arr2.add(obj4);
                obj2.put("buttons", arr2);
            }
        } else if (div.equals("list")) {
            obj9.put("messageText", vo.get("but"));
            obj9.put("label", vo.get("but"));
            obj9.put("action", "message");
            obj2.put("buttons", arr2);
            arr2.add(obj9);
            obj8.put("web", vo.get("tUrl"));
            obj7.put("link", obj8);
            obj7.put("imageUrl", vo.get("tImg"));
            obj7.put("description", vo.get("td"));
            obj7.put("title", vo.get("tt"));
            arr1.add(obj7);
            obj6.put("web", vo.get("sUrl"));
            obj5.put("link", obj6);
            obj5.put("imageUrl", vo.get("sImg"));
            obj5.put("description", vo.get("sd"));
            obj5.put("title", vo.get("st"));
            arr1.add(obj5);
            obj4.put("imageUrl", vo.get("fImg"));
            obj4.put("description", vo.get("f"));
            obj4.put("title", vo.get("ft"));
            arr1.add(obj4);
            obj2.put("items", arr1);
            obj3.put("imageUrl", vo.get("img"));
            obj3.put("title", vo.get("title"));
            obj2.put("header", obj3);
            obj1.put("listCard", obj2);
        }
        obj0.put("message", vo.get("bm"));
        obj0.put("label", vo.get("bm"));
        obj0.put("action", "message");
        arr0.add(obj0);
        arrObj.put("quickReplies", arr0);
        arr.add(obj1);
        arrObj.put("outputs", arr);
        obj.put("template", arrObj);
        obj.put("version", "2.0");
        return obj;
    }

    public void data(Map<String, Object> params, String div, String data) {
        body = this.par(params, "body");
        info = this.par(params, "0");
        if (div.equals("c")) {
            chatHistory.setChatId(chat.getChatId());
            chatHistory.setChatKind("C");
            chatHistory.setUserInfo(info);
            chatHistory.setChatBody(body);
            historyMapper.insertData(chatHistory);
        } else {
            if (data != null) {
                chatHistory.setChatKind("B");
                chatHistory.setChatBody(data);
                historyMapper.insertData(chatHistory);
            } else {
                chat.setChatBody(body);
                chat.setUserCode(chat.getUserCode());
                chatMapper.insertData(chat);
            }
        }
    }

    public String par(Map<String, Object> params, String div) {
        try {
            vo = tem.opsForValue();
            jStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(params);
            JSONObject jPar = (JSONObject) parser.parse(jStr);
            if (div.equals("user")) {
                JSONObject user = (JSONObject) jPar.get("userRequest");
                return user.toString();
            } else if (div.equals("body")) {
                JSONObject user = (JSONObject) jPar.get("userRequest");
                return user.get("utterance").toString();
            } else if (div.equals("ch")) {
                JSONObject act = (JSONObject) jPar.get("action");
                return act.get("name").toString();
            } else {
                JSONObject user = (JSONObject) jPar.get("userRequest");
                JSONObject users = (JSONObject) user.get("user");
                JSONObject props = (JSONObject) users.get("properties");
                return props.get("botUserKey").toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return body;
        }
    }

    public Map<String, Object> func(Map<String, Object> params) {
        ch = this.par(params, "ch");
        body = this.par(params, "body");
        info = this.par(params, "user");

        String bot = this.par(params, "0");
        vo.set(bot, String.valueOf(chat.getChatId()));
        vo.set("bm", "종료");
        if (vo.get(bot).equals("0")) {
            vo.set("ac", String.valueOf(chatMapper.selectUserList(2).getUserCode()));
            vo.set("uc", String.valueOf(chatMapper.selectUserList(796).getUserCode()));
            vo.set("al", chatMapper.selectUserList(2).getUserId() + ", " + chatMapper.selectUserList(2).getUserPw());
            vo.set("ul", chatMapper.selectUserList(796).getUserId() + ", " + chatMapper.selectUserList(796).getUserPw());
            if (ch.contains("0")) {
                return this.login();
            } else if (body.equals(vo.get("al"))) {
                chat.setUserCode(Integer.parseInt(vo.get("ac").toString()));
                this.data(params, "b", null);
                return this.menu(params);
            } else if (body.equals(vo.get("ul"))) {
                chat.setUserCode(Integer.parseInt(vo.get("uc").toString()));
                this.data(params, "b", null);
                return this.menu(params);
            } else if (body.contains(",")) {
                return this.failure();
            } else {
                return this.login();
            }
        } else if (vo.get(bot) != "0") {
            if (ch.contains("0") || body.equals(vo.get("al")) || body.equals(vo.get("ul"))) {
                vo.set("overlap", "1");
                return this.back(params);
            } else if (ch.contains("1")) {
                return this.menu(params);
            } else if (ch.contains("2") || ch.contains("3")) {
                return this.lecture(params);
            } else if (ch.contains("4") || ch.contains("5") || ch.contains("6") || (ch.contains("7") || ch.contains("8"))) {
                return this.exam(params);
            } else if (ch.contains("99")) {
                return this.exit(params);
            }
        }
        return this.back(params);
    }

    public Map<String, Object> login() {
        vo.set("text", "피클봇은 로그인 후에 사용하실 수 있습니다.\n아이디와 비밀번호를 입력해 주세요.\n(형식 : 아이디, 비밀번호)");
        return this.json("sim", "0");
    }

    public Map<String, Object> menu(Map<String, Object> params) {
        vo.set("title", "메뉴를 선택해주세요.");
        vo.set("img", "https://cdn.pixabay.com/photo/2014/05/03/00/46/notebook-336634_1280.jpg");
        vo.set("tt", "화상교육");
        vo.set("td", "다대면 화상교육을 진행합니다.");
        vo.set("tImg", "https://i.pinimg.com/564x/fe/d2/d7/fed2d7cc7ffab16d06138b226735dfae.jpg");
        if (vo.get("ac").equals(String.valueOf(chat.getUserCode()))) {
            title = "출결관리";
            img = "https://i.pinimg.com/564x/3c/02/af/3c02afb940e4aa0e7c9448d75ee0f04f.jpg";
            url = "http://localhost:3000/teacher/attendance";
            title1 = "시험분석";
            img1 = "https://i.pinimg.com/564x/59/e0/28/59e0283e88f18b5c35ed59dbdb869b8d.jpg";
            url1 = "http://localhost:3000/teacherstreaming";
        } else if (vo.get("uc").equals(String.valueOf(chat.getUserCode()))) {
            title = "출석체크";
            img = "https://i.pinimg.com/564x/3c/02/af/3c02afb940e4aa0e7c9448d75ee0f04f.jpg";
            url = "http://localhost:3000/student/attendance";
            title1 = "오답노트";
            img1 = "https://i.pinimg.com/564x/3f/fb/3b/3ffb3ba83f1be537725827d331452695.jpg";
            url1 = "http://localhost:3000/studentstreaming";
        }
        vo.set("tUrl", url1);
        vo.set("st", title);
        vo.set("sd", title);
        vo.set("sImg", img);
        vo.set("sUrl", url);
        vo.set("ft", title1);
        vo.set("f", title1);
        vo.set("fImg", img1);
        vo.set("but", title1);
        this.data(params, "b", null);
        this.data(params, "c", null);
        this.data(params, "b", "menu");
        return this.json("list", "0");
    }

    public Map<String, Object> lecture(Map<String, Object> params) {
        ch = this.par(params, "ch");
        if (ch.contains("2")) {
            title = "화상교육";
            img = "https://cdn.pixabay.com/photo/2018/10/23/10/09/video-recording-3767454_1280.jpg";
        } else if (ch.contains("3")) {
            if (vo.get("ac").equals(String.valueOf(chat.getUserCode()))) {
                title = "출결관리";
                url = "http://localhost:3000/teacher/attendance";
                url1 = "http://localhost:3000/teacherstreaming";
            } else if (vo.get("uc").equals(String.valueOf(chat.getUserCode()))) {
                title = "출석체크";
                url = "http://localhost:3000/student/attendance";
                url1 = "http://localhost:3000/studentstreaming";
            }
            img = "https://media.istockphoto.com/photos/attendance-concept-on-a-computer-display-picture-id1161571987?s=2048x2048";
        } else if (ch.contains("3")) {
            if (vo.get("ac").equals(String.valueOf(chat.getUserCode()))) {
                title = "출결관리";
                url = "http://localhost:3000/teacher/attendance";
                title1 = "화상교육";
            } else if (vo.get("uc").equals(String.valueOf(chat.getUserCode()))) {
                title = "출석체크";
                url = "http://localhost:3000/student/attendance";
                title1 = "화상교육";
            }
            img = "https://media.istockphoto.com/photos/attendance-concept-on-a-computer-display-picture-id1161571987?s=2048x2048";
        }
        vo.set("title", title);
        vo.set("des", title);
        vo.set("img", img);
        vo.set("fUrl", url);
        vo.set("fm", "진행하기");
        this.data(params, "b", null);
        this.data(params, "c", null);
        this.data(params, "b", title);
        return this.json("bas", "bBut");
    }

    public Map<String, Object> exam(Map<String, Object> params) {
        ch = this.par(params, "ch");
        body = this.par(params, "body");
        if (ch.contains("5")) {
            vo.set("text", "과목종류를 선택해주세요.");
            vo.set("fm", "생활과윤리");
            vo.set("sm", "물리");
            vo.set("tm", "생명과학");
            vo.set("fm", "제2외국어");
            if (body.contains("고사")) {
                vo.set("kind", body);
            }
            this.data(params, "b", null);
            this.data(params, "c", null);
            this.data(params, "b", "과목종류");
            return this.json("sim", "sBut");
        } else if (ch.contains("6")) {
            vo.set("sk", body);
            examAnalysis.setUserCode(chat.getUserCode());
            examAnalysis.setExamKind(analysisMapper.codeExamKind(vo.get("kind").toString()));
            examAnalysis.setSubjectCode(analysisMapper.codeSubjectKind(vo.get("sk").toString()));
            ArrayList<ExamAnalysis> analyses = analysisMapper.selectList(examAnalysis);
            for (int i = 0; i < analyses.size(); i++) {
                examAnalysis.setExamNum(analyses.get(i).getExamNum());
                examAnalysis.setWrongAnswer(analyses.get(i).getWrongAnswer());
            }
            exam.setExamNum(examAnalysis.getExamNum());
            exam.setExamKind(examAnalysis.getExamKind());
            exam.setSubjectCode(examAnalysis.getSubjectCode());
            ArrayList<Exam> exams = examMapper.selectList(exam);
            for (int i = 0; i < exams.size(); i++) {
                exam.setExamQuestion(exams.get(i).getExamQuestion());
                exam.setExamChoice1(exams.get(i).getExamChoice1());
                exam.setExamChoice2(exams.get(i).getExamChoice2());
                exam.setExamChoice3(exams.get(i).getExamChoice3());
                exam.setExamChoice3(exams.get(i).getExamChoice3());
                exam.setExamChoice4(exams.get(i).getExamChoice4());
                exam.setExamAnswer(exams.get(i).getExamAnswer());
                exam.setExamContent(exams.get(i).getExamContent());
                if (exams.get(i).getExamChoice5() != "") {
                    exam.setExamChoice5(exams.get(i).getExamChoice5());
                } else {
                    exam.setExamChoice5("없음");
                }
            }
            if (examAnalysis.getExamNum() == 0) {
                title = (vo.get("kind") + " 틀린 문제가 없어요!");
                title1 = "축하합니다. 만점이에요 :)";
            } else {
                title = (vo.get("kind") + " " + vo.get("sk") + " 오답노트");
                title1 = "[" + examAnalysis.getExamNum() + "번 문제] 정답: (" + exam.getExamAnswer() + ") 오답: (" + examAnalysis.getWrongAnswer() + ")" +
                        "\n- " + exam.getExamQuestion() + "\n1. " + exam.getExamChoice1() + " 2. " + exam.getExamChoice2() + " 3. " + exam.getExamChoice3() + " 4. " + exam.getExamChoice4() + " 5. " + exam.getExamChoice5() +
                        "\n해설: " + exam.getExamContent();
            }
            vo.set("title", title);
            vo.set("img", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRDHlNX5vcEeSgkLeAGU9PQ8g3qDFgG7sdrSQ&usqp=CAUhttps://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRDHlNX5vcEeSgkLeAGU9PQ8g3qDFgG7sdrSQ&usqp=CAU");
            vo.set("des", title1);
            vo.set("fm", "메뉴로 이동");
            vo.set("fUrl", "메뉴로 이동");
            this.data(params, "b", null);
            this.data(params, "c", null);
            this.data(params, "b", "오답노트");
            return this.json("bas", "bBut");
        }
        vo.set("text", "시험종류를 선택해주세요.");
        vo.set("fm", "2학기 기말고사");
        vo.set("sm", "2학기 중간고사");
        vo.set("tm", "1학기 기말고사");
        vo.set("fm", "1학기 중간고사");
        this.data(params, "b", null);
        this.data(params, "c", null);
        this.data(params, "b", "시험종류");
        return this.json("sim", "sBut");
    }

    public Map<String, Object> back(Map<String, Object> params) {
        vo.set("fm", "화상교육");
        if (chat.getChatId() != 0) {
            if (vo.get("ac").equals(String.valueOf(chat.getUserCode()))) {
                title = "시험분석";
                title1 = "출결관리";
            } else if (vo.get("uc").equals(String.valueOf(chat.getUserCode()))) {
                title = "오답노트";
                title1 = "출석체크";
            }
            vo.set("sm", title);
            vo.set("tm", title1);
            if (vo.get("overlap").equals("1")) {
                text = "이미 로그인이 되어 있는 상태입니다.";
                vo.set("overlap", "0");
            } else {
                text = "무슨 의미인지 모르겠어요.";
            }
            vo.set("text", text + (" 원하는 단어를 선택해주세요."));
            this.data(params, "b", null);
            this.data(params, "c", null);
            this.data(params, "b", "back");
        }
        return this.json("sim", "sBut");
    }

    public Map<String, Object> failure() {
        vo.set("text", "로그인에 실패하였습니다.\n다시 한 번 정확히 입력해 주세요.\n(형식 : 아이디, 비밀번호)");
        return this.json("sim", "0");
    }

    public Map<String, Object> exit(Map<String, Object> params) {
        vo.set("bm", "");
        vo.set("text", "피클봇이 종료됩니다. 감사합니다.");
        if (chat.getChatId() == 0) {
        } else if (chat.getChatId() != 0) {
            this.data(params, "b", null);
            this.data(params, "c", null);
            this.data(params, "b", "exit");
            chat.setChatId(0);
        }
        return this.json("sim", "0");
    }
}