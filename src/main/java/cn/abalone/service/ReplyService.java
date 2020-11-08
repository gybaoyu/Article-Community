package cn.abalone.service;

import cn.abalone.entity.Reply;
import cn.abalone.entity.User;
import cn.abalone.mapper.ReplyMapper;
import cn.abalone.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static cn.abalone.cache.Cache.*;
import static cn.abalone.entity.Prop.*;

/**
 * Create by Abalone
 * CreateTime: 2020/9/30 22:07
 */

@Service
public class ReplyService {
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JavaMailSender javaMailSender;

    //进行回复操作
    public void reply(Reply reply) {
        replyMapper.addReply(reply);
        if (commentCache)
            reply.setTime(new Date());
            replyCache.add(reply);
        User to = userMapper.selectAllByID(reply.getTo());//要对哪个人回复
        if (to.getEmail() != null && !to.getEmail().equals("")) {
            User from = userMapper.selectAllByID(reply.getUid());//来自于哪个人
            MimeMessage message = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
                helper.setFrom(mailFrom());
                helper.setTo(to.getEmail());
                helper.setText(mailContent(from, to, reply), true);//设置可以使用html
                helper.setSubject("「" + siteName() + "」的邮箱提示");
                javaMailSender.send(message);
            } catch (MessagingException e) {
                return;
//                e.printStackTrace();
            }
        }
    }

    //查询某一个评论下的回复
    public List<Reply> queryReply(Integer from) {
        if (commentsCache!=null) {
            List<Reply> results = new ArrayList<>();
            for (Reply reply : replyCache) {
                if (reply.getFrom().equals(from))
                    results.add(reply);
            }
            return results;
        } else return replyMapper.selectByFrom(from);
    }

    public List<Reply> byAID(Integer aid) {
        if (commentsCache!=null) {
            List<Reply> results = new ArrayList<>();
            for (Reply reply : replyCache) {
                if (reply.getAid().equals(aid))
                    results.add(reply);
            }

            return results;
        } else return replyMapper.selectByAID(aid);
    }

    private String mailContent(User from, User to, Reply reply) {
        return "<div id=\"mailContentContainer\" class=\"qmbox qm_con_body_content qqmail_webmail_only\" style=\"\">\n" +
                "<div style=\"background: white;\n" +
                "      width: 95%;\n" +
                "      max-width: 800px;\n" +
                "      margin: auto auto;\n" +
                "      border-radius: 5px;\n" +
                "      border:orange 1px solid;\n" +
                "      overflow: hidden;\n" +
                "      -webkit-box-shadow: 0px 0px 20px 0px rgba(0, 0, 0, 0.12);\n" +
                "      box-shadow: 0px 0px 20px 0px rgba(0, 0, 0, 0.18);\">\n" +
                "    <header style=\"overflow: hidden;\">\n" +
                "        <img style=\"width:100%;z-index: 666;\" src=\"" + mailImg() + "\">\n" +
                "    </header>\n" +
                "    <div style=\"padding: 5px 20px;\">\n" +
                "        <p style=\"position: relative;\n" +
                "        color: white;\n" +
                "        float: left;\n" +
                "        z-index: 999;\n" +
                "        background: orange;\n" +
                "        padding: 5px 30px;\n" +
                "        margin: -25px auto 0 ;\n" +
                "        box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.30)\">Dear " + to.getName() + "</p>\n" +
                "        <br>\n" +
                "        <h3>您有一条来自<a style=\"text-decoration: none;color: orange \" target=\"_blank\" href=\"" + siteLink() + "\" rel=\"noopener\">" + from.getName() + "</a>的回复</h3>\n" +
                "        <br>\n" +
                "        <p style=\"font-size: 14px;\">" + from.getName() + " 给您的回复如下：</p>\n" +
                "        <p style=\"border-bottom:#ddd 1px solid;border-left:#ddd 1px solid;padding-bottom:20px;background-color:#eee;margin:15px 0px;padding-left:20px;padding-right:20px;border-top:#ddd 1px solid;border-right:#ddd 1px solid;padding-top:20px\">" + reply.getText() + "</p>\n" +
                "        <p style=\"font-size: 14px;\">\n" +
                "            <a style=\"text-decoration: none;color: orange\" target=\"_blank\" href=\"https://zh.abalone.life\" rel=\"noopener\">「" + siteName() + "」</a>&nbsp;双手呈上~\n" +
                "        </p>\n" +
                "        <div style=\"text-align: center;\">\n" +
                "          <img src=\"https://cdn.jsdelivr.net/gh/LIlGG/cdn@1.0.3/img/other/hr.png\" style=\"width:100%;margin:5px auto 5px auto; display: block;\">\n" +
                "          <a style=\"text-transform: uppercase;\n" +
                "                      text-decoration: none;\n" +
                "                      font-size: 14px;\n" +
                "                      border: 2px solid #6c7575;\n" +
                "                      color: #2f3333;\n" +
                "                      padding: 10px;\n" +
                "                      display: inline-block;\n" +
                "                      margin: 10px auto 0; \" target=\"_blank\" href=\"" + siteLink() + "\" rel=\"noopener\">点击查看回复的完整內容</a>\n" +
                "       </div>\n" +
                "       <p style=\"font-size: 12px;text-align: center;color: #999;\">本邮件为系统自动发出，请勿直接回复<br>\n" +
                "        © 2020 「" + siteName() + "」\n" +
                "       </p>\n" +
                "    </div>       \n" +
                "</div><style type=\"text/css\">.qmbox style, .qmbox script, .qmbox head, .qmbox link, .qmbox meta {display: none !important;}</style></div>";
    }

    public List<Reply> cache() {
        return replyMapper.selectAll();
    }
}
