package cn.abalone.mapper;

import cn.abalone.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Create by Abalone
 * CreateTime: 2020/9/30 13:42
 */

@Repository
public interface UserMapper {

    User selectAllByID(@Param("id")Integer id);
    User checkUser(@Param("name")String name,@Param("password") String password);
    User selectAllByName(@Param("name")String name);
    void addUser(@Param("user") User user);
    String nameByID(@Param("id")Integer id);//通过id查询名字

    @Select("select id from chinese.user where name=#{name}")
    Integer IDByName(@Param("name") String name);//通过name查询user
    void updateUser(@Param("user")User user);
}
