package cn.abalone.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Create by Abalone
 * CreateTime: 2020/10/31 9:44
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {
    private Integer id;
    private Integer uid;
    private String filename;
    private String username;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date time;

    public File(Integer uid, String filename, String username, Date time) {
        this.uid = uid;
        this.filename = filename;
        this.username = username;
        this.time = time;
    }
}
