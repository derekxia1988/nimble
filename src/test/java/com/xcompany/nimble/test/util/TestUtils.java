package com.xcompany.nimble.test.util;

import com.xcompany.nimble.biz.exception.BizErrorCode;
import com.xcompany.nimble.biz.exception.BizException;

import static org.junit.jupiter.api.Assertions.*;

public class TestUtils {
    public static void assertBizError(BizErrorCode expected, Runnable runnable) {
        assertEquals(expected, assertThrows(BizException.class, runnable::run).getBizErrorCode());
    }
}
