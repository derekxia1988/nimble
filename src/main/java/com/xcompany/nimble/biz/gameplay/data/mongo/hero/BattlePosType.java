package com.xcompany.nimble.biz.gameplay.data.mongo.hero;

public enum BattlePosType {
    FRONT1(0),
    FRONT2(1),
    BACK1(3),
    BACK2(4),
    BACK3(5)
    ;

    private final int typeCode;

    BattlePosType(int code){
        this.typeCode = code;
    }

    public int getTypeCode() {
        return typeCode;
    }
}
