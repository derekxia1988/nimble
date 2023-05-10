package com.xcompany.nimble.biz.data.protocol.Response;

import com.xcompany.nimble.biz.data.mongo.Hero;
import com.xcompany.nimble.biz.data.mongo.Item;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RespLevelUpData implements RespData {
    private int lordLevel = 0;

    private Map<String, Hero> heroMap = new HashMap<>();

    private Map<String, Item> itemMap = new HashMap<>();

    public void setLordLevel(int lordLevel) {
        this.lordLevel = lordLevel;
    }

    public Map<String, Hero> getHeroMap() {
        return heroMap;
    }

    public Map<String, Item> getItemMap() {
        return itemMap;
    }
}
