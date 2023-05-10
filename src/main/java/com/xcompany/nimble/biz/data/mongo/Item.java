package com.xcompany.nimble.biz.data.mongo;

import lombok.Builder;

@Builder
public class Item {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
