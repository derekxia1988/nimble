package com.xcompany.nimble.biz.gameplay.data.mongo.hero;


public enum TeamType {
    MAIN_PVE(1);

    private final int typeCode;

    TeamType(int code){
        this.typeCode = code;
    }

    public int getTypeCode() {
        return typeCode;
    }
}
