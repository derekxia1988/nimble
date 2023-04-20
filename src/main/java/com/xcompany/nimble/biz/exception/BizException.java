package com.xcompany.nimble.biz.exception;

public class BizException extends RuntimeException{
    private final BizErrorCode bizErrorCode;
    public BizException(BizErrorCode bizErrorCode, String msg) {
        super(String.format("[biz error]%s: %s", bizErrorCode.getErrorCode(), msg));
        this.bizErrorCode = bizErrorCode;
    }

    public BizErrorCode getBizErrorCode() {
        return bizErrorCode;
    }
}
