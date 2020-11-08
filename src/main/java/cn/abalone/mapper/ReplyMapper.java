package cn.abalone.mapper;

import cn.abalone.entity.Reply;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Create by Abalone
 * CreateTime: 2020/9/30 21:39
 */

@Repository
public interface ReplyMapper {
    void addReply(@Param("reply") Reply reply);//添加回复
    List<Reply> selectByFrom(@Param("from") Integer from);//通过评论id查询回复
    @Select("select * from chinese.reply where aid=#{aid}")
    List<Reply> selectByAID(@Param("aid") Integer aid);
    @Select("select * from chinese.reply")
    List<Reply> selectAll();
}
