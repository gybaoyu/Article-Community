package cn.abalone.mapper;

import cn.abalone.entity.File;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Create by Abalone
 * CreateTime: 2020/10/31 9:46
 */

@Repository
public interface FileMapper {
    void upload(@Param("file")File file);
    @Select("select * from chinese.file where username=#{username}")
    void getFileByUserName(@Param("username")String username);
    @Select("select * from chinese.file where uid=#{uid}")
    void getFileByUserID(@Param("uid")Integer uid);
}
