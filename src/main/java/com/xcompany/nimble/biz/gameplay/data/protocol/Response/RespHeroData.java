package com.xcompany.nimble.biz.gameplay.data.protocol.Response;

import com.xcompany.nimble.biz.gameplay.data.mongo.hero.Hero;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RespHeroData implements RespData {

    private Map<String, Hero> heroMap = new HashMap<>();

    public Map<String, Hero> getHeroMap() {
        return heroMap;
    }
}
