package cn.abalone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create by Abalone
 * CreateTime: 2020/10/24 9:55
 * article like和view的缓存中间类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeAndView  implements Comparable<LikeAndView>{
    private Integer like;
    private Integer view;
    public void addView(){
        ++view;
    }
    public void addLike(){
        ++like;
    }

    @Override
    public int compareTo(LikeAndView o) {
        return o.getLike()-this.like;
    }
}
