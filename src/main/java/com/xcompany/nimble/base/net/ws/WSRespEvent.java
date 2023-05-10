package com.xcompany.nimble.base.net.ws;

import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import org.springframework.context.ApplicationEvent;

public class WSRespEvent extends ApplicationEvent {
    private final String pid;

    private final Object msg;

    public WSRespEvent(Object source, String pid, Object msg) {
        super(source);
        this.pid = pid;
        this.msg = msg;
    }

    public String getPid() {
        return pid;
    }

    public Object getMsg() {
        return msg;
    }
}
