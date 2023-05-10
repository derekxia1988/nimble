package com.xcompany.nimble.biz.data;

import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Map;

@Document("player")
@Data
@Builder
public class Player {
    @Id
    private String id;

    private Map<Integer, Hero> heroMap;

    private Map<Integer, Item> itemMap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<Integer, Hero> getHeroMap() {
        return heroMap;
    }

    public void setHeroMap(Map<Integer, Hero> heroMap) {
        this.heroMap = heroMap;
    }

    public Map<Integer, Item> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<Integer, Item> itemMap) {
        this.itemMap = itemMap;
    }

    public void init(){
        this.heroMap = Maps.newHashMap();
        this.itemMap = Maps.newHashMap();
        this.getHeroMap().put(20001, Hero.builder().id(20001).level(1).build());
        this.getHeroMap().put(20002, Hero.builder().id(20002).level(1).build());
        this.getHeroMap().put(20003, Hero.builder().id(20003).level(1).build());
        this.getHeroMap().put(30001, Hero.builder().id(30001).level(1).build());
        this.getHeroMap().put(30002, Hero.builder().id(30002).level(1).build());
        this.getItemMap().put(1, Item.builder().count(100000).build());
    }
}
