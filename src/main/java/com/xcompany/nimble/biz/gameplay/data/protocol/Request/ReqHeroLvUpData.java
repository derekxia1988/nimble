package com.xcompany.nimble.biz.gameplay.data.protocol.Request;

import lombok.Data;

@Data
public class ReqHeroLvUpData extends ReqData {
    private String heroId;

    private int level;

    public int getLevel() {
        return level;
    }

    public String getHeroId() {
        return heroId;
    }
}
