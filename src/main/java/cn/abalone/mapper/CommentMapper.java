package cn.abalone.mapper;

import cn.abalone.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Create by Abalone
 * CreateTime: 2020/9/30 21:38
 */

@Repository
public interface CommentMapper {
    @Select("select * from chinese.comment")
    List<Comment>selectAll();

    void addComment(@Param("comment") Comment comment);
    List<Comment>selectByAID(@Param("aid")Integer aid);
    Integer[] selectByUID(@Param("uid")Integer uid);
    @Select("select * from chinese.comment where id=#{id}")
    Comment selectByID(@Param("id")Integer id);
    void addChat(@Param("chat")Comment comment);
    void delete(@Param("id")Integer id);
    void deleteReply(@Param("id")Integer id);
}
