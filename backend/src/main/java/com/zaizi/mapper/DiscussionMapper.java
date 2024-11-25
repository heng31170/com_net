package com.zaizi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zaizi.pojo.Discussion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface DiscussionMapper extends BaseMapper<Discussion> {
    // 根据问题id获取讨论
    @Select("select * from discussions where question_id = #{questionsId}")
    List<Discussion> getDiscussionsByQueId(Integer questionsId);
}
