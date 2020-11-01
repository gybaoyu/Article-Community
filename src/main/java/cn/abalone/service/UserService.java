package cn.abalone.service;

import cn.abalone.entity.User;
import cn.abalone.mapper.UserMapper;
import cn.abalone.util.Pinyin4jUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Create by Abalone
 * CreateTime: 2020/9/30 13:54
 */

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User allByID(Integer id) {
        return userMapper.selectAllByID(id);
    }

    public User allByName(String name) {
        return userMapper.selectAllByName(name);
    }

    public Integer IDByName(String name) {
        return userMapper.IDByName(name);
    }

    public String nameByID(Integer id) {
        return userMapper.nameByID(id);
    }

    public void addUser(User user) {
        userMapper.addUser(user);
    }

    /**
     * 更新用户信息
     *
     * @param user 前端传的用户
     * @return 返回一个寂寞
     */
    public void updateUser(User user) {
        User tmp = userMapper.selectAllByID(user.getId());
        /*以下是一堆脑残的非空判断*/
        if (user.getPassword() == null) {
            user.setPassword(tmp.getPassword());//更改密码
        }
        if (user.getEmail() == null) {
            user.setEmail(tmp.getEmail());//更改邮箱
        }
        if (user.getType() == null) {
            user.setType(tmp.getType());//更改权限
        }
        if (user.getSess() == null) {
            user.setSess(tmp.getSess());//更改届数
        }
        if (user.getClazz() == null) {
            user.setClazz(tmp.getClazz());//更改班级
        }
        /*脑残的非空判断结束了, ( •̀ ω •́ )y 好开心*/
        userMapper.updateUser(user);
    }

    /**
     * 检测cookie存值,查询用户信息
     *
     * @param request 当前的request会话
     * @return 查询后的user
     */
    public User byCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("key"))
                    return allByID(Integer.valueOf(cookie.getValue()));
            }
        }
        return null;
    }

    /**
     * 检测账号信息
     *
     * @param name     前端的用户名
     * @param password 密码
     * @return 是否查询到数据
     */
    public boolean checkUser(String name, String password) {
        return userMapper.checkUser(name, password) != null;
    }

    /**
     * cookie存值
     *
     * @param id 直接存id
     */
    public void addCookie(HttpServletResponse response, Integer id) {
        String key = id.toString();
        Cookie c = new Cookie("key", key);
        c.setMaxAge(2592000);
        response.addCookie(c);
    }

    /**
     * 注销账号
     *
     * @param cookies 当前的cookie
     */
    public void logout(Cookie[] cookies, HttpServletResponse response) {
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    /**
     * 从excel表格中读取数据加入到数据库
     *
     * @param path  excel路径
     * @param clazz 学生班级
     * @param sess  学生届数
     * @return 返回添加后的日志用于展示
     */
    public StringBuilder excel(String path, Integer clazz, Integer sess) {
        Pinyin4jUtil util = new Pinyin4jUtil();
        StringBuilder log = new StringBuilder();
        File xlsFile = new File(path);
        List<String> name = new ArrayList<>();
        List<User> user = new ArrayList<>();
        //创建工作簿
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(new FileInputStream(xlsFile));
            //读取第一个工作表
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            //获取最后一行的num，即总行数。此处从0开始计数
            int maxRow = sheet.getLastRowNum();
            log.append("总行数为：" + maxRow + "\r\n");
            for (int row = 0; row <= maxRow; row++) {
                //获取最后单元格num，即总单元格数 注意：此处从1开始计数
                System.out.println(sheet.getRow(row));
                int maxRol = sheet.getRow(row).getLastCellNum();
                log.append("--------第" + row + "行的数据如下--------\r\n");
                for (int rol = 0; rol < maxRol; rol++) {
                    name.add(String.valueOf(sheet.getRow(row).getCell(rol)));
                    log.append(sheet.getRow(row).getCell(rol) + "  ");
                }
                log.append("\r\n");
            }
            xssfWorkbook.close();
        } catch (IOException e) {
            log.append(Arrays.toString(e.getStackTrace()));
        }
        for (String s : name) {
            if (allByName(s) == null)
                user.add(new User(s, util.getStringPinYin(s), 0, "", clazz, sess));
            else
                log.append("===================" + s + "已存在 ===================");
        }
        for (User u : user) {
            addUser(u);
        }
        log.append("共添加了"+user.size()+"个数据");
        return log;
    }
}
