package com.xcompany.nimble.biz.common;

public enum BizModule {
    BASE(1),
    USER(2),
    CULTIVATION(3),
    RESOURCE(4)
    ;
    private final int code;

    BizModule(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
