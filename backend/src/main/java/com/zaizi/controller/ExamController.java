package com.zaizi.controller;


import com.zaizi.pojo.Exam;
import com.zaizi.pojo.ExamAnswer;
import com.zaizi.pojo.ExamAnswerWithUser;
import com.zaizi.pojo.ExamWithUser;
import com.zaizi.service.ExamAnsService;
import com.zaizi.service.ExamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ExamController {
    @Autowired
    private ExamService examService;

    @Autowired
    private ExamAnsService examAnsService;
    // 获取所有考试
    @GetMapping("/exams")
    public List<ExamWithUser> getAllExams() {
        return examService.getAllExams();
    }
    // 根据考试id获取考试
    @GetMapping("/exam/{examId}")
    public Exam getExamById(@PathVariable Integer examId) {
        return examService.getExamById(examId);
    }
    // 添加考试
    @PostMapping("/exam/add")
    public void addExam(@RequestBody Exam exam) {
        examService.addExam(exam);
    }
    // 删除考试
    @PostMapping("/exam/del/{examId}")
    public void delExam(@PathVariable Integer examId) {
        examService.delExam(examId);
    }
    // 添加答案
    @PostMapping("/exam/ans/add")
    public void addAns(@RequestBody ExamAnswer examAnswer) {
        examAnsService.addAns(examAnswer);
    }
    // 删除答案
    @PostMapping("/exam/ans/del/{answerId}")
    public void delAns(@PathVariable Integer answerId) {
        examAnsService.delAns(answerId);
    }
    // 根据考试获取答案
    @GetMapping("/exam/ans/{examId}")
    public List<ExamAnswerWithUser> getExamAnsById(@PathVariable Integer examId) {
        return examAnsService.getAnswersByExamId(examId);
    }
    // 更新评分
    @PostMapping("/exam/ans/score")
    public ResponseEntity<String> updateScore(@RequestBody ExamAnswer examAnswer) {
        Integer answerId = examAnswer.getAnswerId();
        Integer score = examAnswer.getScore();
        log.info("评分操作,id:{answerId},得分:{score}");
        boolean updated = examAnsService.updateScore(answerId, score);
        if (updated) {
            return ResponseEntity.ok("评分更新成功");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("答案未找到");
        }
    }
}
