package com.xcompany.nimble.biz.data.protocol.Request;

public class ReqBase {
    private String pid;
    private int opCode;

    private ReqData reqData;

    public String getPid() {
        return pid;
    }

    public int getOpCode() {
        return opCode;
    }
}
