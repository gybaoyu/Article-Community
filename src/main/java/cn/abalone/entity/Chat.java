package cn.abalone.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Create by Abalone
 * CreateTime: 2020/10/7 19:45
 * 讨论实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    private Integer id;
    private String name;
    private String to;
    private String text;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date time;
}
