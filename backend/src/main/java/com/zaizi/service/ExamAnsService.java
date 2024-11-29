package com.zaizi.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zaizi.mapper.ExamAnsMapper;
import com.zaizi.pojo.ExamAnswer;
import com.zaizi.pojo.ExamAnswerWithUser;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ExamAnsService extends ServiceImpl<ExamAnsMapper, ExamAnswer> {
    // 根据考试id获取答案
    public List<ExamAnswerWithUser> getAnswersByExamId(Integer examId) {
        return baseMapper.getAnswersByExamId(examId);
    }
    // 添加答案
    public void addAns(ExamAnswer examAnswer) {
        this.save(examAnswer);
    }
    // 删除答案
    public void delAns(Integer answerId) {
        baseMapper.deleteById(answerId);
    }
    // 更新评分
    public boolean updateScore(Integer answerId, Integer score) {
        ExamAnswer examAnswer = new ExamAnswer();
        examAnswer.setScore(score); // 设置新评分

        // 使用 UpdateWrapper 设置条件
        UpdateWrapper<ExamAnswer> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("answer_id", answerId); // 根据 answerId 更新

        return this.update(examAnswer, updateWrapper); // 返回更新结果
    }
}
