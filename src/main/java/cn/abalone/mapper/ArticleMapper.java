package cn.abalone.mapper;

import cn.abalone.entity.Article;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Create by Abalone
 * CreateTime: 2020/10/1 10:18
 */


@Repository
public interface ArticleMapper {

    void createArticle(@Param("article") Article article);//添加文章
    int lastInsertID();//获取最近添加的文章的ID(用于缓存)

    void updateArticle(@Param("article") Article article);//更新文章

//    List<Article> getArticleList();//仅仅用于首页列表的Article集合,里面的对象字段不全
//
//    List<Article> getByIsPass(@Param("pass") Boolean pass);//通过审核状态查询文章,仅仅用于审核列表的集合,字段不全

//    @Select("select * from chinese.article where")
//    List<Article>search(@Param("text")String text);//搜索功能

    @Select("select * from chinese.article")
    List<Article> getCacheArticle();//获取所有文章所有信息

    @Select("select id,view,`like` from chinese.article")
    List<Article> getCacheLikeAndView();

    @Delete("delete article from chinese.article where id=#{id}")
    void deleteArticle(@Param("id") Integer id);//删除文章

    @Update("update chinese.article set pass = 1,view=0,`like`=0 where id=#{id}")
    void passOne(@Param("id") Integer id);//审核单个文章

    @Update("update chinese.article set pass = 1,view=0,`like`=0 where pass=0")
    void passAll();//审核全部

//    @Select("select * from chinese.article where id=#{id}")
//    Article getArticle(@Param("id") Integer id);//通过id查询

//    @Select("select * from chinese.article where uid=#{aid}")
//    List<Article> getAllFromOne(@Param("aid") Integer aid);//查询一个用户的所有文章

    @Update("update chinese.article set view=#{view} where id=#{id}")
    void changeView(@Param("id") Integer id,@Param("view")Integer view);//改变访问量

    @Update("update chinese.article set `like`=#{like} where id=#{id}")
    void changeLike(@Param("id") Integer id,@Param("like")Integer like);//改变点赞数

    List<Article> byLike(@Param("size") Integer size);

    void setTop(@Param("id") Integer id, @Param("top") boolean top);

//    List<Article> getTop();
}
