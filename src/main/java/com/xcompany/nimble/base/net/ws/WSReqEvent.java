package com.xcompany.nimble.base.net.ws;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.ApplicationEvent;

public class WSReqEvent extends ApplicationEvent {
    private JSONObject jsonObject;
    public WSReqEvent(Object source, JSONObject jsonObject) {
        super(source);
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
