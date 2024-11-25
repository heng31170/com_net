package com.zaizi.controller;


import com.zaizi.pojo.Discussion;
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
    public List<Discussion> getDiscussions(@PathVariable Integer questionId) {
        return discussionService.getDiscussionsByQueId(questionId);
    }
    // 添加讨论
    @PostMapping("/discussion/add")
    public void Discussion(@RequestBody Discussion discussion) {
        discussionService.addDiscussion(discussion);
    }
}
