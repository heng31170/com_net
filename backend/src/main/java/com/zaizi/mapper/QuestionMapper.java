package com.zaizi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zaizi.pojo.DiscussionQuestion;
import com.zaizi.pojo.QuestionWithUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface QuestionMapper extends BaseMapper<DiscussionQuestion> {
    // 获取问题及用户
    @Select("SELECT dq.question_id, dq.course_id, dq.user_id, dq.content, dq.created_at, u.username, u.name " +
            "FROM discussion_questions dq " +
            "JOIN users u ON dq.user_id = u.user_id")
    List<QuestionWithUser> getAllQuestionsWithUsers();
}
