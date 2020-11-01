package cn.abalone.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create by Abalone
 * CreateTime: 2020/9/30 13:11
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String password;
    private Integer type;//1访客 0学生 -1管理员
    private String email;
    private Integer clazz;//表示n班的学生
    private Integer sess;//表示第n届的学生

    public User(String name, String password, Integer type, String email, Integer clazz, Integer sess) {
        this.name = name;
        this.password = password;
        this.type = type;
        this.email = email;
        this.clazz = clazz;
        this.sess = sess;
    }
}
