package com.xcompany.nimble.biz.data.mongo;

import lombok.Builder;

@Builder
public class Hero {
    private int id;

    private int level = 1;
}
