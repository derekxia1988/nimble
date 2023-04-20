package com.xcompany.nimble.biz.common;

import java.time.Duration;

public enum RedisKey {
    USER_NAME("user:name:%s", Duration.ofMinutes(1)),
    ;
    private final String key;
    private final Duration expire;

    RedisKey(String key, Duration expire) {
        this.key = key;
        this.expire = expire;
    }

    public String getKey(Object... args) {
        return String.format(key, args);
    }

    public Duration getExpire() {
        return expire;
    }
}
