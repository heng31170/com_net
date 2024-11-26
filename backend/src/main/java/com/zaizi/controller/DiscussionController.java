package com.zaizi.controller;


import com.zaizi.pojo.Discussion;
import com.zaizi.pojo.DiscussionWithUser;
import com.zaizi.service.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DiscussionController {
    @Autowired
    private DiscussionService discussionService;
    // 获取讨论
    @GetMapping("/discussion/{questionId}")
    public List<DiscussionWithUser> getDiscussions(@PathVariable Integer questionId) {
        return discussionService.getDiscussionsByQueId(questionId);
    }
    // 添加讨论
    @PostMapping("/discussion/add")
    public void addDiscussion(@RequestBody Discussion discussion) {
        discussionService.addDiscussion(discussion);
    }
    // 删除讨论
    @PostMapping("/discussion/del/{discussionId}")
    public void delDiscussion(@PathVariable Integer discussionId) {
        discussionService.delDiscussion(discussionId);
    }
}
