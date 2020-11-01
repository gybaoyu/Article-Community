package cn.abalone.mapper;

import cn.abalone.entity.Chat;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Create by Abalone
 * CreateTime: 2020/10/7 19:46
 */

@Repository
public interface ChatMapper {
    @Select("select * from chinese.chat where `to`=-1")
    List<Chat>getChat();

    @Select("select * from chinese.chat where `to`!=-1")
    List<Chat>getReplyChat();

    @Select("select * from chinese.chat")
    List<Chat> getAll();

    void addChat(@Param("chat") Chat chat);

    @Delete("delete chat from chinese.chat where id=#{id}")
    void delete(@Param("id") Integer id);
}
