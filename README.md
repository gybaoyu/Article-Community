# 声明: 仅供学习,请勿商用 :-)

![JDK](https://img.shields.io/badge/JDK-1.8-red.svg)
![MySQL](https://img.shields.io/badge/MySQL-8.0.15-yellow.svg)
![Maven](https://img.shields.io/badge/Maven-3.5.2-blue.svg)
[![LICENSE](https://img.shields.io/badge/license-MIT-black.svg)](https://gitee.com/gybaoyu/Article-Community/blob/master/LICENSE)

## 项目简介

> ​之所以开发这个项目,实际上单纯的是因为我语文老师想要搞一个能把班级同学的作文传上去的公众号,然鹅我觉得公众号太麻烦了🤮因此就和Daman一起写出了这个项目(目前还在开发中)


## 本项目用于记录学生的文章

具体功能: 

- 支持学生/游客 **注册**,**登录**本站,并且发表文章 (需要管理员在后台审核过关)
- 能够进行**查看文章**,**点赞**,**评论**等操作
- 能够**留言,**支持一些**基本的个人信息展示**等
- 支持excel批量注册学生账号

也就是说以后再有学生写了什么文章就可以发表在这里

相比公众号,作文周报等的**优势**:

1. **随时随地**能够访问	(只要连接了网络)
2. **任何人都能访问,不限于学校**
3. 发表文章时可以在**任意场所**,如: 可以在家,学校(只要你有电子设备), 因此可以避免学生在学校用老师手机占用在学校的学习时间,(学生可以在家中操作)
4. 维护更方便,看起来的效果也更好



## 项目地址

**GitHub:** https://github.com/gybaoyu/Article-Community

**Gitee:** https://gitee.com/gybaoyu/Article-Community



## 快速开始

#### 针对于centos7.6用户,部署在服务器上

**项目使用环境:**

- MySQL8+Java8: 具体教程详见:https://abalone.life/archives/java8mysql8config

安装好环境之后,进行以下操作:

1. 下载本项目的jar包: https://gitee.com/gybaoyu/Article-Community/releases

2. 新建文件夹,上传项目

   ```shell
   cd /home
   mkdir chinese
   cd chinese
   # 然后将文件上传到本文件夹,你可以使用wget或者xshell的rz -y指令
   # wget使用方法: wget 文件下载路径
   # 如: wget https://gitee.com/gybaoyu/Article-Community/attach_files/506765/download/chinese-0.0.1-SNAPSHOT.jar
   ```

3. 更改配置文件

   ```shell
   # 在/home/chinese中输入:
   wget https://abalone.life/upload/2020/10/application-b1bc7941f52b431795dff04c474bd3d9.yml
   # 然后输入
   mv application-b1bc7941f52b431795dff04c474bd3d9.yml application.yml
   ```

4. 搭建数据库环境

   ```shell
   # 在https://gitee.com/gybaoyu/Article-Community/releases中下载sql后缀的文件,一般文件名为data.sql,为建表数据,然后登录mysql,输入:
   create database chinese
   use chinese
   # 然后将data.sql文件用记事本打开,复制里面的内容,粘贴到服务器上,回车即可
   ```

5. 最后启动项目

   ```shell
   nohup java -jar /home/chinese/chinese-0.0.1-SNAPSHOT.jar --spring.config.location=application.yml &
   ```

然后就可以开始使用了~

**(实际上在其他的系统也可以正常部署,本教程仅仅为新手提供,实际上就是一个单纯的项目部署而已)**

## 开发文档

详见: 
![Gitee](https://gitee.com/gybaoyu/Article-Community/blob/master/API.md)
![GitHub](https://github.com/gybaoyu/Article-Community/blob/master/API.md)
