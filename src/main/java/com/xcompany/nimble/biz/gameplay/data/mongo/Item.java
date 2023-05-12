package com.xcompany.nimble.biz.gameplay.data.mongo;

import lombok.Builder;

@Builder
public class Item {
    private int count;

    private int id;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int incr(int incr){
        this.count += incr;
        return this.count;
    }
}
