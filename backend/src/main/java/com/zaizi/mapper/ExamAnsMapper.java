package com.zaizi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zaizi.pojo.ExamAnswer;
import com.zaizi.pojo.ExamAnswerWithUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExamAnsMapper extends BaseMapper<ExamAnswer> {
    // 根据考试id获取考试答案及用户
    @Select("SELECT ea.answer_id, ea.exam_id, ea.user_id, ea.content, ea.created_at,ea.score, u.username, u.name " +
            "FROM exam_answers ea " +
            "JOIN users u ON ea.user_id = u.user_id " +
            "WHERE ea.exam_id = #{examId}")
    List<ExamAnswerWithUser> getAnswersByExamId(Integer examId);
}
