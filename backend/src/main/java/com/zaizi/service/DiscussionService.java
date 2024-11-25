package com.zaizi.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zaizi.mapper.DiscussionMapper;
import com.zaizi.pojo.Discussion;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DiscussionService extends ServiceImpl<DiscussionMapper, Discussion> {
    // 根据问题id获取讨论
    public List<Discussion> getDiscussionsByQueId(Integer questionsId) {
        return baseMapper.getDiscussionsByQueId(questionsId);
    }
    // 添加讨论
    public void addDiscussion(Discussion discussion) {
        this.save(discussion);
    }
}