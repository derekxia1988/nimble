package com.xcompany.nimble.biz.gameplay.data.protocol.Response;

public enum RespOpCode {
    ERROR(-1),
    LOGIN(10001),
    STAGE_UNLOCK(20001),
    LORD_LEVEL_UP(30000),
    HERO_CHANGE(30001),
    ITEM_CHANGE(40001),
    ;

    private final int opCode;

    RespOpCode(int opCode){
        this.opCode = opCode;
    }

    public int getOpCode() {
        return opCode;
    }
}
