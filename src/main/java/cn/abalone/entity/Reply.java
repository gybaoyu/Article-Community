package cn.abalone.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Create by Abalone
 * CreateTime: 2020/9/30 20:58
 * 评论的子回复
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    private Integer aid;//文章id
    private Integer uid;//当前登录用户的id,来自于哪个人
    private Integer from;//对应comment的id,来自于哪条评论
    private Integer to;//表示要回复给某人,此处为某人的id,发给哪个人
    private String text;//回复内容
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date time;//回复时间

    public Reply(Integer from, Integer to, String text) {
        this.from = from;
        this.to = to;
        this.text = text;
    }
}