package com.group9.musicweb.util;

import java.io.Serializable;

/**
 * 用于封装服务器到客户端JSON的返回值
 *
 * @author zhuming
 * @date 2017/12/18
 */
public class ResultRequest implements Serializable {
    /**
     * 用来存储返回的状态
     */
    private boolean state = true;
    /**
     * 用来存储返回的消息
     */
    private String message;
    /**
     * 用来存储返回的数据
     */
    private Object obj;

    public ResultRequest() {
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
