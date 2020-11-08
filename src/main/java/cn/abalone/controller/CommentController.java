package cn.abalone.controller;

import cn.abalone.entity.Comment;
import cn.abalone.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Create by Abalone
 * CreateTime: 2020/9/30 21:52
 * 负责评论与回复的controller
 */

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 添加评论
     * 评论对象,由前端返回
     * 需要验证ackey
     */
    @PostMapping("/comment")
    public void comment(@RequestBody Comment comment) {
        System.out.println(comment);
        commentService.comment(comment);
    }

    /**
     * 通过文章id查询评论
     *
     * @param aid 文章id
     */
    @GetMapping("/queryComment/{aid}")
    public List<Comment> queryc(@PathVariable("aid") Integer aid) {
        return commentService.queryComment(aid);
    }

    //如果是null则为0
    @GetMapping("/countCommentByUID/{uid}")
    public Integer byUID(@PathVariable("uid")Integer uid){
        return commentService.selectByUID(uid);
    }

    @GetMapping("/deleteComment/{id}")
    public void delete(@PathVariable Integer id) {
        commentService.deleteChat(id);
    }
}
