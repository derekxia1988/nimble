package com.xcompany.nimble.biz.gameplay.data.protocol.Response;

import com.xcompany.nimble.biz.gameplay.data.mongo.hero.Hero;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RespHeroData implements RespData {
    private int lordLevel = 0;

    private Map<String, Hero> heroMap = new HashMap<>();

    public void setLordLevel(int lordLevel) {
        this.lordLevel = lordLevel;
    }

    public Map<String, Hero> getHeroMap() {
        return heroMap;
    }
}
