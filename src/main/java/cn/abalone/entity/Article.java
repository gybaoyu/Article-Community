package cn.abalone.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Create by Abalone
 * CreateTime: 2020/10/1 10:06
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private Integer id;
    private Integer uid;//作者id
    private Integer view;//访问量
    private Integer like;//赞

    private String author;//作者名
    private String title;
    private String content;//文章内容
    private String des;//文章简介
    private String tag;//文章标签
    private String img;//文章封面图片
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;//创建时间

    private Boolean pass;//是否审核过关
    private Boolean top;//是否置顶

    public Article(Integer uid, String title, String content, String tag, String img, String des) {
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.img = img;
        this.des = des;
        this.time = new Date();
    }
}
