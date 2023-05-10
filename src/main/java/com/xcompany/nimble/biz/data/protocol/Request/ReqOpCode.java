package com.xcompany.nimble.biz.data.protocol.Request;

public enum ReqOpCode {
    LOGIN(10001),
    STAGE_SUCCESS(20001),
    LORD_LEVEL_UP(30000),
    HERO_LEVEL_UP(30001)
    ;

    private final int opCode;

    ReqOpCode(int opCode){
        this.opCode = opCode;
    }

    public int getOpCode() {
        return opCode;
    }
}
