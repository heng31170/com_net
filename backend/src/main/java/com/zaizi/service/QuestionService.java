package com.zaizi.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zaizi.mapper.QuestionMapper;
import com.zaizi.pojo.DiscussionQuestion;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuestionService extends ServiceImpl<QuestionMapper, DiscussionQuestion> {
    // 获取所有问题
    public List<DiscussionQuestion> getAllQuestions() {
        return list();
    }
    // 根据id获取详细问题
    public DiscussionQuestion getQuestionById(Integer questionId) {
        return getById(questionId);
    }
    // 添加问题
    public void addQuestion(DiscussionQuestion discussionQuestion) {
        this.save(discussionQuestion);
    }
}