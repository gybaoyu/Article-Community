package cn.abalone.controller;

import cn.abalone.dto.LikeAndView;
import cn.abalone.entity.Article;
import cn.abalone.entity.Comment;
import cn.abalone.service.ArticleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static cn.abalone.cache.Cache.*;


/**
 * Create by Abalone
 * CreateTime: 2020/10/1 10:57
 */

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/addArticle")
    public void add(@RequestBody Article article) {
        articleService.create(article);
    }

    @PostMapping("/updateArticle")
    public void update(@RequestBody Article article) {
        articleService.update(article);
    }

    @DeleteMapping("/deleteArticle/{id}")
    public void delete(@PathVariable("id") Integer id) {
        articleService.delete(id);
    }

    @GetMapping("/passArticle/{id}")
    public void passOne(@PathVariable Integer id) {
        articleService.passOne(id);
    }

    @GetMapping("/passAll")
    public void passAll() {
        articleService.passAll();
    }

    /**
     * 通过boolean获取(已审核/未审核)的Article
     * 里面的字段不完整
     *
     * @param pass true/false,true表示已审核
     * @return 存放了Article的List
     */
    @GetMapping("/getArticleByPass/{pass}")
    public List<Article> getpList(@PathVariable("pass") boolean pass) {
        return articleService.byPass(pass);
    }

    /**
     * 注意: 这里仅仅保存了首页文章列表展示需要的字段
     *
     * @return 首页文章列表需要的信息
     */
    @GetMapping("/getAllArticle")
    public List<Article> getAllA() {
        return articleService.allArticles();
    }

    /**
     * 用于文章详情页面
     * 通过id得到一篇文章的所有信息
     *
     * @param id 文章id
     * @return 返回Article中所有的字段
     */
    @GetMapping("/getArticleByID/{id}")
    public Article allDataForArticleByID(@PathVariable("id") Integer id) {
        return articleService.allDataForArticleByID(id);
    }

    /**
     * 能够查出某个作者的所有文章
     *
     * @param aid 作者id
     * @return 所有文章且Article中的所有字段
     */
    @GetMapping("/getArticleByAuthor/{aid}")
    public List<Article> allArticleForOneAuthor(@PathVariable("aid") Integer aid) {
        return articleService.allArticleForOneAuthor(aid);
    }

    /**
     * @param now  当前是第几页 默认第一页的数据
     * @param size 每一页显示多少条数据 默认10条
     * @return 分页后的集合
     */
    @GetMapping("/getArticleForIndex")
    public List<Article> forIndex(@RequestParam(value = "now", defaultValue = "1") Integer now,
                                      @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return articleService.getAllArticleForPage(now, size);
    }

    //点赞
    @GetMapping("/like")
    public void like(@RequestParam("aid") Integer aid) {
        articleService.like(aid);
    }

    /**
     * 点赞数排行
     *
     * @param size 尺寸,要显示几个就传几个
     */
    @GetMapping("/byLike/{size}")
    public List<Article> byLike(@PathVariable(value = "size") Integer size) {
        return articleService.byLike(size);
    }

    //获取置顶的文章
    @GetMapping("/getTop")
    public List<Article> top() {
        return articleService.getTop();
    }

    //将某一篇文章设为置顶/取消置顶
    @GetMapping("/setTop")
    public void stop(@RequestParam("top") boolean top,
                     @RequestParam("id") Integer id) {
        articleService.setTop(id, top);
    }

    //获取当前缓存
    @GetMapping("/getCache")
    public void getCache() {
        for (Map.Entry<Integer, Article> entry : articleCache.entrySet()) {
            System.out.println("Key: " + entry.getKey() + "\r\n Value: " + entry.getValue());
        }

        System.out.println("==========================");

        for (Map.Entry<Integer, LikeAndView> entry : likeViewCache.entrySet()) {
            System.out.println("Key: " + entry.getKey() + "\r\n Value: " + entry.getValue());
        }
        System.out.println("==========================");
        for (Map.Entry<Integer, List<Comment>> entry : commentsCache.entrySet()) {
            System.out.println("Key: " + entry.getKey() + "\r\n Value: " + entry.getValue());
        }
        System.out.println("==========================");
        System.out.println(replyCache);
    }

    //同步缓存到数据库
    @GetMapping("/cache")
    public void cache() {
        articleService.synchronousCache();
    }

    @GetMapping("/search")
    public PageInfo<Article> search(@RequestParam("text") String text,
                                    @RequestParam(value = "now", defaultValue = "1") Integer now,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return articleService.search(text, now, size);
    }
}