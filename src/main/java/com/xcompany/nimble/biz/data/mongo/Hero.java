package com.xcompany.nimble.biz.data.mongo;

import lombok.Builder;

@Builder
public class Hero {
    private String id;

    private int level = 1;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
