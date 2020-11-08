package cn.abalone.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Create by Abalone
 * CreateTime: 2020/9/30 16:36
 */

//读取配置文件的信息
@Component
public class Prop {
    private static String ackey;
    private static String filePath;
    private static String mailFrom;
    private static String img;
    private static String name;
    private static String link;

    /**
     * 1.若将该注解加到静态变量上，不会被加载;
     * 2.访问修饰符修饰为私有即可，spring在加载时，能找得到；
     */
    @Value("${site.ackey}")
    private void setAckey(String ackey) {
        Prop.ackey = ackey;
    }
    public static String ackey() {
        return ackey;
    }

    @Value("${site.filePath}")
    private void setFilePath(String filePath) {
        Prop.filePath = filePath;
    }
    public static String filePath() {
        return filePath;
    }

    @Value("${site.link}")
    private void setLink(String link) {
        Prop.link = link;
    }
    public static String siteLink() {
        return link;
    }

    @Value("${site.name}")
    private void setName(String name) {
        Prop.name = name;
    }
    public static String siteName() {
        return name;
    }

    @Value("${site.img}")
    private void setImg(String img) {
        Prop.img = img;
    }
    public static String mailImg() {
        return img;
    }

    @Value("${site.mailFrom}")
    private void setMailFrom(String mailFrom) {
        Prop.mailFrom = mailFrom;
    }
    public static String mailFrom() {
        return mailFrom;
    }
}
