package cn.abalone.controller;

import cn.abalone.entity.Chat;
import cn.abalone.service.ChatService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Create by Abalone
 * CreateTime: 2020/10/7 19:59
 */

@RestController
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping("/chat")
    public void add(@RequestBody Chat chat) {
        chatService.addChat(chat);
    }

    @GetMapping("/queryChat")
    public PageInfo<Chat> queryChat(@RequestParam(value = "now", defaultValue = "1") int now,
                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        return chatService.getChatForIndex(now, size);
    }

    @GetMapping("/queryReplyChat")
    public List<Chat> queryReplyChat() {
        return chatService.getReplyChatForIndex();
    }

    @GetMapping("/deleteChat/{id}")
    public void delete(@PathVariable Integer id) {
        chatService.deleteChat(id);
    }
}
