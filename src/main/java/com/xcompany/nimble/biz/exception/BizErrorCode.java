package com.xcompany.nimble.biz.exception;

import com.xcompany.nimble.biz.common.BizModule;

import static com.xcompany.nimble.biz.common.BizModule.*;

public enum BizErrorCode {
    // ************* BASE **********************
    /**
     * 未知错误
     */
    UNKNOWN_ERROR(BASE, 1),
    // ************* USER **********************
    /**
     * 用户已存在
     */
    USER_ALREADY_EXISTS(USER, 1),
    /**
     * 用户不存在或密码错误
     */
    USER_NOT_FOUND(USER, 2);
    private final BizModule module;
    /**
     * 每个模块的错误码都可以从1开始
     */
    private final int code;

    BizErrorCode(BizModule module, int code) {
        this.module = module;
        this.code = code;
    }

    public String getErrorCode() {
        return String.format("%d%03d", module.getCode(), code);
    }

    public BizException error(String msg) {
        return new BizException(this, msg);
    }
}
