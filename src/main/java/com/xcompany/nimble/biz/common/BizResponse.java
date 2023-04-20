package com.xcompany.nimble.biz.common;

public class BizResponse<T> {
    private static final String SUCCESS_CODE = "0";
    private final String code;
    private final T data;

    public static <T> BizResponse<T> success(T data) {
        return new BizResponse<>(SUCCESS_CODE, data);
    }

    public static <T> BizResponse<T> success() {
        return new BizResponse<>(SUCCESS_CODE, null);
    }

    public static <T> BizResponse<T> fail(String code) {
        return new BizResponse<>(code, null);
    }

    public static BizResponse<String> fail(String code, String msg) {
        return new BizResponse<>(code, msg);
    }

    private BizResponse(String code, T data) {
        this.code = code;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public T getData() {
        return data;
    }
}
