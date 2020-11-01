package cn.abalone.mapper;

import cn.abalone.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Create by Abalone
 * CreateTime: 2020/9/30 21:38
 */

@Repository
public interface CommentMapper {
    void addComment(@Param("comment") Comment comment);
    List<Comment>selectByAID(@Param("aid")Integer aid);
    Integer[] selectByUID(@Param("uid")Integer uid);
}
