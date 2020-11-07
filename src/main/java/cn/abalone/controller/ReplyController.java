package cn.abalone.controller;

import cn.abalone.entity.Reply;
import cn.abalone.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Create by Abalone
 * CreateTime: 2020/9/30 21:52
 * 负责评论与回复的controller
 */

@RestController
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    /**
     * 添加回复
     * 回复对象,由前端返回
     * 需要验证ackey
     */
    @PostMapping("/reply")
    public void reply(@RequestBody Reply reply){
        replyService.reply(reply);
    }

    /**
     * 通过评论id查询回复
     * @param from 评论id
     */
    @GetMapping("/queryReply/{from}")
    public List<Reply> queryc(@PathVariable("from") Integer from) {
        return replyService.queryReply(from);
    }

    /**
     * 通过文章查询回复
     *
     * @param aid 文章id
     * @return 存储了Reply的集合
     */
    @GetMapping("/getReplyByArticleID/{aid}")
    public List<Reply> byAID(@PathVariable Integer aid) {
        return replyService.byAID(aid);
    }
}
