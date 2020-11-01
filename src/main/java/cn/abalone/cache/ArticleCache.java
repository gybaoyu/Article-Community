package cn.abalone.cache;

import cn.abalone.dto.LikeAndView;
import cn.abalone.entity.Article;
import cn.abalone.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by Abalone
 * CreateTime: 2020/10/24 9:39
 */

@Component
public class ArticleCache {
    public static Map<Integer, Article> articleCache = new HashMap<>();//所有文章的缓存,注意,这里的Integer存的不是article的id,而是hashmap的下标;
    public static Map<Integer, LikeAndView> likeViewCache = new HashMap<>();//点赞和访问量的缓存
    public static List<Integer> haveUpdated= new ArrayList<>();//储存被修改过的数据的id
    @Autowired
    private ArticleService articleService;

    @PostConstruct
    public void setArticleCache() {
        List<Article> articles = articleService.cache();
        List<Article> likeAndView = articleService.likeAndViewCache();
        System.out.println("系统启动中.....");

        System.out.println("加载articleCache");

        for (Article article : articles) {
            articleCache.put(article.getId(), article);
//            System.out.println(article.getId());
        }

        System.out.println("加载likeViewCache");
        for (Article article : likeAndView) {
            likeViewCache.put(article.getId(), new LikeAndView(article.getLike(), article.getView()));
//            System.out.println(article.getId());
        }
    }
}
