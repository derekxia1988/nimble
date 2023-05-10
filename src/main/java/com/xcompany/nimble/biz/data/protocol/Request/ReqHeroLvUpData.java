package com.xcompany.nimble.biz.data.protocol.Request;

import lombok.Data;

@Data
public class ReqHeroLvUpData implements ReqData {
    private String heroId;

    private int level;

    public int getLevel() {
        return level;
    }

    public String getHeroId() {
        return heroId;
    }
}
