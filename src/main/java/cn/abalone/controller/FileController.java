package cn.abalone.controller;

import cn.abalone.service.FileService;
import cn.abalone.service.UserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static cn.abalone.entity.Prop.filePath;

/**
 * Create by Abalone
 * CreateTime: 2020/10/25 6:45
 */

@RestController
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;

    /**
     * 上传文件到服务器
     *
     * @param file 上传的文件
     * @return success表示成功, empty表示文件为空, fail表示出了什么奇怪的问题
     */
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("username") String username,
                         @RequestParam("uid") Integer uid) {

        String filename = file.getOriginalFilename();
        File path = new File(filePath() + "/" + username + "/" + filename);
        if (file.isEmpty()) {
            return "empty";
        }
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), path);
            fileService.upload(new cn.abalone.entity.File(uid, filename, username, new Date()));
            return "success:" + path;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }

    @PostMapping("/addUserByExcel")
    public Object upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("clazz") Integer clazz,
                         @RequestParam("sess") Integer sess) {

        String filename = file.getOriginalFilename();
        File path = new File(filePath() + "/root/excel/" + filename);
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), path);
            return userService.excel(path.getPath(), clazz, sess);
        } catch (IOException e) {
            return "出现异常"+e.getMessage();
        }catch (ArrayIndexOutOfBoundsException | NullPointerException e){
            e.printStackTrace();
            return "请检查excel中的数据是否有非中文字符!并且确保所有字符都在第一列最左侧或第一行最上方";
        }
    }
}
