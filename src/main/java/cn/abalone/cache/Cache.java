package cn.abalone.cache;

import cn.abalone.dto.LikeAndView;
import cn.abalone.entity.Article;
import cn.abalone.entity.Comment;
import cn.abalone.entity.Reply;
import cn.abalone.service.ArticleService;
import cn.abalone.service.CommentService;
import cn.abalone.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class Cache {
    public static Map<Integer, Article> articleCache = new HashMap<>();//所有文章的缓存,注意,这里的Integer存的不是article的id,而是hashmap的下标;
    public static Map<Integer, LikeAndView> likeViewCache = new HashMap<>();//点赞和访问量的缓存
    public static Map<Integer, List<Comment>> commentsCache = new HashMap<>();//点赞和访问量的缓存
    public static List<Reply> replyCache = new ArrayList<>();//点赞和访问量的缓存
    public static List<Integer> haveUpdated= new ArrayList<>();//储存被修改过的数据的id

    @Value("${site.commentCache}")
    private boolean comment_cache;
    public static boolean commentCache;

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ReplyService replyService;

    @PostConstruct
    public void setCache() {
        commentCache=comment_cache;
        List<Article> articles = articleService.cache();
        List<Article> likeAndView = articleService.likeAndViewCache();
        List<Comment> comments = commentService.cache();

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

        if (commentCache) {
            System.out.println("加载comments,chat,reply");
            for (Comment comment : comments) {
                int key = comment.getAid();
                //如果缓存中没有这个评论的key,那么就新建一个
                if (!commentsCache.containsKey(key))
                    commentsCache.put(key, new ArrayList<>());
                    //如果已经有了的话,就直接向map的value中的list加值
                else commentsCache.get(key).add(comment);
//            System.out.println(article.getId());
            }
            replyCache = replyService.cache();
        } else {
            System.out.println("未开启comments,chat,reply的缓存");
        }
    }
}
