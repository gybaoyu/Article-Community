package cn.abalone.service;

import cn.abalone.entity.File;
import cn.abalone.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by Abalone
 * CreateTime: 2020/10/25 6:52
 */

@Service
public class FileService {
    @Autowired
    private FileMapper fileMapper;

    public void upload(File file){
        fileMapper.upload(file);
    }
}

