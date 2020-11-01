package cn.abalone.service;

import cn.abalone.entity.Chat;
import cn.abalone.mapper.ChatMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Create by Abalone
 * CreateTime: 2020/10/7 19:54
 */

@Service
public class ChatService {
    @Autowired
    private ChatMapper chatMapper;

    public void addChat(Chat chat) {
        chat.setTime(new Date());
        chatMapper.addChat(chat);
    }

    public void deleteChat(Integer id) {
        chatMapper.delete(id);
    }

    public PageInfo<Chat> getChatForIndex(int pageNow, int pageSize) {
        PageHelper.startPage(pageNow, pageSize, "`time` desc");
        return new PageInfo<>(chatMapper.getChat());
    }

    public List<Chat> getReplyChatForIndex() {
        return chatMapper.getReplyChat();
    }
}
