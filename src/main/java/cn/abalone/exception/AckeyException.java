package cn.abalone.exception;

import lombok.NoArgsConstructor;

import java.io.PrintStream;

/**
 * Create by Abalone
 * CreateTime: 2020/9/30 17:18
 */

@NoArgsConstructor
public class AckeyException extends Exception{
    public AckeyException(String msg){
        super(msg);
    }
}
