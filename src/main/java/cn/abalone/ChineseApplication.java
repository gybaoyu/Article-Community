package cn.abalone;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static cn.abalone.entity.Prop.*;

@MapperScan("cn.abalone.mapper") //扫描的mapper
@SpringBootApplication
public class ChineseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChineseApplication.class, args);
        System.out.println("ackey: "+ackey());
        System.out.println("mailFrom: "+mailFrom());
        System.out.println("mailImg: "+mailImg());
        System.out.println("siteLink: "+siteLink());
        System.out.println("siteName: "+siteName());
        System.out.println("filePath: "+filePath());
    }
}
