package cn.abalone.service;

import cn.abalone.dto.LikeAndView;
import cn.abalone.entity.Article;
import cn.abalone.mapper.ArticleMapper;
import cn.abalone.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static cn.abalone.cache.ArticleCache.*;

/**
 * Create by Abalone
 * CreateTime: 2020/10/1 10:41
 */

@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper uMapper;

    public List<Article> cache() {
        return articleMapper.getCacheArticle();
    }

    public List<Article> likeAndViewCache() {
        return articleMapper.getCacheLikeAndView();
    }

    public void create(Article article) {
        article.setTime(new Date());
        article.setPass(false);
        article.setTop(false);
        article.setAuthor(uMapper.nameByID(article.getUid()));
        articleMapper.createArticle(article);
        int tmp = articleMapper.lastInsertID();
        articleCache.put(tmp, article);//先在数据库中添加,利用返回的主键再加到缓存
        likeViewCache.put(tmp, new LikeAndView(0, 0));
    }

    public void update(Article article) {
        article.setTime(new Date());
        articleMapper.updateArticle(article);
        articleCache.put(article.getId(), article);
    }

    public void delete(Integer id) {
        articleMapper.deleteArticle(id);
        articleCache.remove(id);
    }

    public void passOne(Integer id) {
        articleMapper.passOne(id);
        Article tmp = articleCache.get(id);
        tmp.setPass(true);
        articleCache.put(id, tmp);
    }

    public void passAll() {
        articleMapper.passAll();
        for (Map.Entry<Integer, Article> entry : articleCache.entrySet()) {
            Article tmp = articleCache.get(entry.getKey());
            tmp.setPass(true);
            articleCache.put(entry.getKey(), tmp);
        }
    }

    public void like(Integer id) {
        //点赞
        LikeAndView tmp = likeViewCache.get(id);
        tmp.addLike();
        likeViewCache.put(id, tmp);
        if (!haveUpdated.contains(id)) {
            haveUpdated.add(id);
        }
    }

    public void setTop(Integer id, boolean top) {
        articleMapper.setTop(id, top);
        Article tmp = articleCache.get(id);
        tmp.setTop(top);
        articleCache.put(id, tmp);
    }

    public List<Article> getTop() {
        List<Article> tmp = new ArrayList<>();
        for (Map.Entry<Integer, Article> entry : articleCache.entrySet()) {
            Article article = articleCache.get(entry.getKey());
            if (article.getTop())
                tmp.add(article);
        }
        return tmp;
    }

    public List<Article> allArticleForOneAuthor(Integer aid) {
        List<Article> tmp = new ArrayList<>();
        //查询一个用户的所有文章
        for (Map.Entry<Integer, Article> entry : articleCache.entrySet()) {
            Article article = articleCache.get(entry.getKey());
            if (article.getUid().equals(aid))
                tmp.add(article);
        }
        return tmp;
    }

    public List<Article> byPass(Boolean pass) {
        List<Article> tmp = new ArrayList<>();
        //通过审核状态查询文章
        for (Map.Entry<Integer, Article> entry : articleCache.entrySet()) {
            Article article = articleCache.get(entry.getKey());
            if (article.getPass() == pass)
                tmp.add(article);
        }
        return tmp;
    }

    public List<Article> allArticles() {
        //获取首页文章列表需要的信息
        List<Article> result = new LinkedList<>();
        Set<Map.Entry<Integer, Article>> eSet = articleCache.entrySet();
        for (Map.Entry<Integer, Article> integerArticleEntry : eSet) {
            if (integerArticleEntry.getValue().getPass()) {
                result.add(integerArticleEntry.getValue());
            }
        }
        return result;
    }

    //查询一篇文章,并且增加访问量
    public Article allDataForArticleByID(Integer id) {
        //增加访问量
        LikeAndView likeAndView = likeViewCache.get(id);
        likeAndView.addView();
        if (!haveUpdated.contains(id)) {
            haveUpdated.add(id);
        }
        //返回文章
        return articleCache.get(id);
    }

    /**
     * 分页查询
     *
     * @param pageNow  当前页面
     * @param pageSize 每页的数据数量
     * @return 返回文章list
     */
    public PageInfo<Article> getAllArticleForPage(int pageNow, int pageSize) {
        List<Article> result = new ArrayList<>();
        Set<Map.Entry<Integer, Article>> eSet = articleCache.entrySet();
        for (Map.Entry<Integer, Article> i : eSet) {
            if (i.getValue().getPass()) {
                result.add(i.getValue());
            }
        }
        PageHelper.startPage(pageNow, pageSize, "`time` desc");
        return new PageInfo<>(result);
    }

    /**
     * 根据点赞数排行
     *
     * @param size 一共取出前几个点赞最多的
     * @return 返回List集合
     */
    public List<Article> byLike(Integer size) {
        return articleMapper.byLike(size);
    }

    /**
     * 同步缓存,主要是同步点赞和访问量
     */
    public void synchronousCache() {
        for (Integer i : haveUpdated) {
            articleMapper.changeLike(i, likeViewCache.get(i).getLike());
            articleMapper.changeView(i, likeViewCache.get(i).getView());
        }
    }

    public PageInfo<Article> search(String text, int pageNow, int pageSize) {
        List<Article> result = new LinkedList<>();
        Set<Map.Entry<Integer, Article>> eSet = articleCache.entrySet();
        for (Map.Entry<Integer, Article> e : eSet) {
            Article tmp = e.getValue();
            if (tmp.getAuthor().contains(text)) {
                result.add(tmp);
                continue;
            }
            if (tmp.getContent().contains(text)) {
                result.add(tmp);
            }
        }
        PageHelper.startPage(pageNow, pageSize, "`time` desc");
        return new PageInfo<>(result);
    }
}
