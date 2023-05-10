package com.xcompany.nimble.biz.data.protocol.Response;


import com.alibaba.fastjson.JSON;
import lombok.Builder;

@Builder
public class RespBase {
    private final String pid;
    private final int opCode;
    private final RespData respData;

    public String getPid() {
        return pid;
    }

    public int getOpCode() {
        return opCode;
    }

    public RespData getRespData() {
        return respData;
    }
}
