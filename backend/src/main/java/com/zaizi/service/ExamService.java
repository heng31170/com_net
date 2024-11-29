package com.zaizi.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zaizi.mapper.ExamMapper;
import com.zaizi.pojo.Exam;
import com.zaizi.pojo.ExamWithUser;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ExamService extends ServiceImpl<ExamMapper, Exam> {
    // 获取考试及用户
    public List<ExamWithUser> getAllExams() {
        return baseMapper.getAllExamsWithUsers();
    }
    // 根据考试id获取考试
    public Exam getExamById(Integer examId) {
        return this.getById(examId);
    }
    // 添加考试
    public void addExam(Exam exam) {
        this.save(exam);
    }
    // 删除考试
    public void delExam(Integer examId) {
        baseMapper.deleteById(examId);
    }
}
