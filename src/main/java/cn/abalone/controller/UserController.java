package cn.abalone.controller;

import cn.abalone.entity.User;
import cn.abalone.service.UserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import static cn.abalone.entity.Prop.filePath;


/**
 * Create by Abalone
 * CreateTime: 2020/9/30 14:24
 */

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录api
     *
     * @param user 前端传来的user
     * @return 返回是否登录成功
     */
    @PostMapping("/login")
    public boolean login(@RequestBody User user, HttpServletResponse response) {
        if (userService.checkUser(user.getName(), user.getPassword())) {
            userService.addCookie(response, userService.IDByName(user.getName()));
            return true;
        }
        return false;
    }

    /**
     * 获取当前的User对象(如果cookie存了的话)
     *
     * @param request 当前会话
     * @return 查出的数据
     */
    @GetMapping("/getCookieUser")
    public User getUser(HttpServletRequest request) {
        return userService.byCookie(request);
    }

    /**
     * 查询用户名是否重复
     */
    @PostMapping("/register")
    public boolean reg(@RequestBody User user) {
        if (userService.allByName(user.getName()) == null) {
            //true则表示查询结果为null,也就是不重名,否则相反
            userService.addUser(user);
            return true;
        }
        return false;
    }

    //注销账号
    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        userService.logout(request.getCookies(), response);
    }

    /**
     * 通过id获取user的一切信息
     *
     * @param id 要获取的user对象的id
     * @return 返回查询的对象
     */
    @GetMapping("/getUserByID/{id}")
    public User allByID(@PathVariable Integer id) {
        return userService.allByID(id);
    }

    //通过id查询用户名
    @GetMapping("/getUserNameByID/{id}")
    public String nameByID(@PathVariable Integer id) {
        return userService.nameByID(id);
    }

    //通过name查询用户(的全部信息)
    @GetMapping("/getUserByName/{name}")
    public User uByName(@PathVariable("name") String name) {
        return userService.allByName(name);
    }

    //通过name查询id
    @GetMapping("/getIDByName/{name}")
    public Integer getIDByName(@PathVariable("name") String name) {
        return userService.IDByName(name);
    }

    /**
     * @param user 注意user中的id必填
     */
    @PostMapping("/updateUser")
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }
}
