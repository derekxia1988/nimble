package com.xcompany.nimble.biz.gameplay.data.mongo.stage;

public enum StageType {
    MAIN_PVE_STAGE(0),
    BACK3(5)
    ;

    private final int typeCode;

    StageType(int code){
        this.typeCode = code;
    }

    public int getTypeCode() {
        return typeCode;
    }
}
