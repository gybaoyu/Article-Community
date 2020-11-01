# API前端开发文档

**API命名规范:  如果路径中有{xxx},例:/getUserByID/{id},则表示id要使用REST架构来传,
除此外为RequestParam或ResponseBody,分别对应GET和POST
(只要没有标'POST'的都是GET或其他请求)**

### UserController

- /login =>**login(User `user`) boolean POST**  判断用户账号密码是否正确,true则登录,存cookie

  - user中必填`name`和`password`字段

- /register =>**reg(User `user`) boolean POST**  判断用户名是否重复,false则添加新用户(表示未重名)

  - 当type为游客,user中必填`name`, `password`, `type`字段
  - 当type为学生,user中必填`name`, `password`, `type`, `clazz`, `sess`字段

- /getUserByID/{id} =>**allByID(Integer id) User**  通过id获取user的**一切**信息
  
- /logout =>logout() void  去除cookie,注销账号
  
- /getCookieUser =>getUser() User  获取当前cookie的User的**一切**信息
  
- /getUserNameByID/{id} =>nameByID(Integer `id`) String通过id**只获取用户名**

- /getIDByName/{name} => getIDByName(String `name`) Integer **通过name获取id**

- /getUserByName/{name} => uByName(String `name`)User 通过name获取所有数据

- /updateUser =>updateUser(User `user`) 更新用户信息 **POST**

  - 可更新的字段有 `password ` `email ` `sess ` `clazz ` `type`
  
  - user中必填id,尽量不要只有id(免得浪费性能)

  - 意思就是说不能所有表单都是空(最好不要,要不然会被恶意刷性能)
  
  - #### 一定要做空字符串判断!!!!否则如果用户输入的是""也会被update的!
  

### CommentController

- /comment => **comment(Comment `comment`) void POST**  用于添加评论

  - comment中必填`aid`, `text`字段
  
- /queryComment/{aid} =>queryc(Integer `aid`) List<Comment>  通过文章id查询评论


- /countCommentByUID/{uid} => byUID(Integer `uid`)通过uid查询评论数量
  - **如果返回为null表示0条评论**



### ReplyController

- /reply => **reply(Reply `reply`) void POST** 用于添加回复

  - reply中必填`from`,` text`, `to`字段
  
- /queryReply/{from} =>queryc(Integer `from`)List<Reply>  通过评论id查询回复  
  
- /getReplyByArticleID/{aid} =>byAID(Integer aid) List<Reply> 通过文章id查询回复



### ArticleController

- /addArticle => **add(Article `article`)void POST**用于添加文章
  
  - article中需要字段: `uid` `title` `content` `des` `tag` `img`
  - 如果没有des建议传一个""而不是null
- /updateArticle => **update(Article `article`) void POST** 用于更新文章

  - article中需要字段: `id` `img` `tag` `content` `title` `des`
  - 请注意! 这里面的每一个字段都不能为空,如果用户将某一个字段的文字全部删除了,请用""代替null !!!!!
  - 因此要先用getArticleByID获取到当前文章的所有信息(然后填充到表单里)
- /deleteArticle/{id} => **delete(Integer `id`) void DELETE** 用于删除文章

  - 请注意,需要使用**DeleteMapping**(GetMapping我没有测试过,懒得改了)
- /passArticle/{id} => **passOne(Integer id)void** 用于审核文章
- /passAll => **passAll()void  审核所有未审核的文章**
- /getArticleByPass/{pass} => getpList(boolean `pass`) List<Article>  获得所有(已审核/未审核)的文章,看你给的参数是啥

  - 返回的每一个article中的有效字段只有`id`,`author`,`title`,`time`,**记得传参**
-  /getAllArticle => getAllA() List<Article> 获取所有文章

   -  **请注意: 此处每一个article中的字段只有`id`,`author`,` title`,` des`, `view`, `like`, `time`,(用于首页的文章列表)**
- /getArticleByID/{id} => allDataForArticleByID(Integer `id`) Article 通过id获取一个Article的所有信息,**同时会增加访问量**
- /getArticleByAuthor/{aid} => allArticleForOneAuthor(Integer `aid`) List<Article>
  
  -  能够查出某个作者的所有文章,并且article对象的**所有字段**都会被查出来
- /getArticleForIndex =>forIndex(Integer `now`,Integer `size`)PageInfo<Article>获取分页所需数据(已经分页了的)
  - now: 当前是第几页,默认为第一页的数据
  - size: 每一页显示多少条数据,默认十条
  - 返回的PageInfo<Article>中会带有你需要的api,具体可以看参数名字理解或者查pagehelper文档
- /like => like(Integer `aid`) 查询一个文章的赞的数量,aid必填
- /byLike/{size} byLike(Integer size)List<Article> 通过点赞数排序
  
  - 需要多少个,size就写多少,比如点赞前5就写5
- /setTop=> stop(boolean `top`,Integer `id`) void  设置置顶/取消置顶
- /getTop=> top() List<Article> 获取置顶的文章
- /getCache=> getCache void 控制台输出缓存的数据
- /cache => cache void 同步缓存到数据库
- /search => search(String `text`,Integer `now`,Integer `size` )
  - text:用户搜索的文本
  - now: 当前是第几页,默认为第一页的数据
  - size: 每一页显示多少条数据,默认十条

### ChatController

- /chat=> add(Chat `chat`)void 添加chat **POST**  **RequestBody**
  - chat对象中必填: from,to,text
  - from: **string** 当前登录的用户
  - to: **string** 回复给的用户; text:  string 内容
  - 注意from和to是string

- /deleteChat/{id}=>delete(Integer `id`)void 通过id删除chat
- /queryChat=>query(int `now`,int `size`)获取非回复的留言(已分页) PageInfo<Chat>
  - now: 当前第几页 默认1
  - size: 每页的条数 默认10
- /queryReplyChat => queryReplyChat()获取是回复的留言(to字段不为-1的,未分页) List<Chat>

### FileController

- /upload => upload(MultipartFile `file`,`username`,`uid`) POST
  - file为用户上传的文件,注意用MultipartFile
  - username和uid分别为当前用户的信息,通过/getCookieUser获取
  - 返回码: empty表示空文件,success表示上传成功,fail表示上传失败
  - **返回success后面会跟着路径名,例: success:D:/usr/xxxx.jpg**
  - 其中success冒号后面的就是上传的文件路径了(也就是配置文件路径+用户名+文件名)

- /addUserByExcel => excel(String `path`,Integer `clazz`,Integer `sess`) Object POST 通过excel导入学生数据,并且返回日志

  - 返回值为Object,当作是String即可,需要弹出一个提示框展示返回的log(也就是返回值,因为那里面会有导入学生的一些信息)
  - 请注意表格中的数据只能有中文,学生名字需要全都在最左边第一列或最上面第一行,否则会返回报错信息,不影响数据