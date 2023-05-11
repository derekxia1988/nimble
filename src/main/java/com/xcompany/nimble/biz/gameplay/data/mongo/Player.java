package com.xcompany.nimble.biz.gameplay.data.mongo;

import com.google.common.collect.Maps;
import com.xcompany.nimble.biz.gameplay.data.mongo.hero.TeamType;
import com.xcompany.nimble.biz.gameplay.data.mongo.stage.StageType;
import com.xcompany.nimble.biz.gameplay.data.mongo.hero.BattlePosType;
import com.xcompany.nimble.biz.gameplay.data.mongo.hero.Hero;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document("player")
@Data
public class Player {
    @Id
    private String id;

    private int lordLevel;

    private Map<String, Hero> heroMap = Maps.newHashMap();

    private Map<String, Item> itemMap = Maps.newHashMap();

    private Map<String, Map<String, String>> teamMap = Maps.newHashMap();

    private Map<String, Integer> stageProgressMap = Maps.newHashMap();

    public Player(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getLordLevel() {
        return lordLevel;
    }

    public void setLordLevel(int lordLevel) {
        this.lordLevel = lordLevel;
    }

    public Map<String, Hero> getHeroMap() {
        return heroMap;
    }

    public Map<String, Item> getItemMap() {
        return itemMap;
    }

    public Map<String, Map<String, String>> getTeamMap() {
        return teamMap;
    }

    public Map<String, Integer> getStageProgressMap() {
        return stageProgressMap;
    }

    public void init(){
        this.lordLevel= 1;

        this.getHeroMap().put("20001", Hero.builder().id("20001").level(1).build());
        this.getHeroMap().put("20002", Hero.builder().id("20002").level(1).build());
        this.getHeroMap().put("20003", Hero.builder().id("20003").level(1).build());
        this.getHeroMap().put("30001", Hero.builder().id("30001").level(1).build());
        this.getHeroMap().put("30002", Hero.builder().id("30002").level(1).build());
        this.getItemMap().put("1", Item.builder().count(100000).build());
        this.getStageProgressMap().put(String.valueOf(StageType.MAIN_PVE_STAGE.getTypeCode()), 10000101);

        this.teamMap.put(String.valueOf(TeamType.MAIN_PVE.getTypeCode()), Maps.newHashMap());
        this.teamMap.get(String.valueOf(TeamType.MAIN_PVE.getTypeCode())).put(String.valueOf(BattlePosType.FRONT1.getTypeCode()), "20001");
        this.teamMap.get(String.valueOf(TeamType.MAIN_PVE.getTypeCode())).put(String.valueOf(BattlePosType.FRONT2.getTypeCode()), "20002");
        this.teamMap.get(String.valueOf(TeamType.MAIN_PVE.getTypeCode())).put(String.valueOf(BattlePosType.BACK1.getTypeCode()), "20003");
        this.teamMap.get(String.valueOf(TeamType.MAIN_PVE.getTypeCode())).put(String.valueOf(BattlePosType.BACK2.getTypeCode()), "30001");
        this.teamMap.get(String.valueOf(TeamType.MAIN_PVE.getTypeCode())).put(String.valueOf(BattlePosType.BACK3.getTypeCode()), "30002");
    }
}
