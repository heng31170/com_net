package com.zaizi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zaizi.pojo.Discussion;
import com.zaizi.pojo.DiscussionWithUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface DiscussionMapper extends BaseMapper<Discussion> {
    // 根据问题id获取讨论
    @Select("SELECT d.discussion_id, d.question_id, d.user_id, d.content, d.created_at, u.username, u.name " +
            "FROM discussions d " +
            "JOIN users u ON d.user_id = u.user_id " +
            "WHERE d.question_id = #{questionsId}")
    List<DiscussionWithUser> getDiscussionsByQueId(Integer questionsId);
}
