package cn.abalone.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create by Abalone
 * CreateTime: 2020/9/30 20:57
 * 评论
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Integer id;
    private Integer uid;
    private Integer aid;//表示所在文章的id
    private String text;//回复内容
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date time;//回复时间

    public Comment(Integer aid, String text) {
        this.aid = aid;
        this.text = text;
    }

    public Comment(Integer aid, String text, Date time) {
        this.aid = aid;
        this.text = text;
        this.time = time;
    }
}
