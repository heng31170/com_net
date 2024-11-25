package com.zaizi.controller;


import com.zaizi.pojo.DiscussionQuestion;
import com.zaizi.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    // 获取所有问题
    @GetMapping("/questions")
    public List<DiscussionQuestion> getAllQuestions() {
        return questionService.getAllQuestions();
    }
    // 根据id获取问题
    @GetMapping("/question/{questionId}")
    public DiscussionQuestion getQuestionById(@PathVariable Integer questionId) {
        return questionService.getQuestionById(questionId);
    }
    // 添加问题
    @PostMapping("/question/add")
    public void addQuestion(@RequestBody DiscussionQuestion discussionQuestion) {
        questionService.addQuestion(discussionQuestion);
    }
}
