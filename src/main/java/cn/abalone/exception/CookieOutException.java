package cn.abalone.exception;

import lombok.NoArgsConstructor;

/**
 * Create by Abalone
 * CreateTime: 2020/9/30 15:03
 */

//表示cookie已经过期了
@NoArgsConstructor
public class CookieOutException extends Exception {
    public CookieOutException(String msg){
        super(msg);
    }
}
