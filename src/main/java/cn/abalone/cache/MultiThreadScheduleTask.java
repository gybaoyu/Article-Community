package cn.abalone.cache;

import cn.abalone.entity.Article;
import cn.abalone.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static cn.abalone.cache.Cache.*;

/**
 * Create by Abalone
 * CreateTime: 2020/10/24 11:59
 * 定时任务
 */

//@Component注解用于对那些比较中立的类进行注释；
//相对与在持久层、业务层和控制层分别采用 @Repository、@Service 和 @Controller 对分层中的类进行注释
@Component
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启多线程
public class MultiThreadScheduleTask {
    @Autowired
    private ArticleService articleService;

    @Async
    @Scheduled(fixedDelay = 1800000)  //间隔30min,同步点赞,访问量到数据库
    public void first() throws InterruptedException {
//        System.out.println("开始同步缓存 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
        articleService.synchronousCache();
    }

    @Async
    @Scheduled(fixedDelay = 60000)  //间隔1min,同步点赞,访问量到静态集合
    public void second() throws InterruptedException {
//        System.out.println("开始同步缓存 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
        for (Integer id : haveUpdated) {
            Article article = articleCache.get(id);
            article.setLike(likeViewCache.get(id).getLike());
            article.setView(likeViewCache.get(id).getView());
            articleCache.put(id, article);
        }
    }

}
