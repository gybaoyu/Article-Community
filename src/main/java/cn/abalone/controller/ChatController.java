package cn.abalone.controller;

import cn.abalone.entity.Comment;
import cn.abalone.service.CommentService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Create by Abalone
 * CreateTime: 2020/10/7 19:59
 */

@RestController
public class ChatController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/chat")
    public void add(@RequestBody Comment comment) {
        commentService.addChat(comment);
    }

    @GetMapping("/queryChat")
    public PageInfo<Comment> queryChat(@RequestParam(value = "now", defaultValue = "1") int now,
                                       @RequestParam(value = "size", defaultValue = "10") int size) {
        return commentService.getChatForIndex(now, size);
    }
}
