package com.xcompany.nimble.biz.gameplay.data.mongo;

import com.google.common.collect.Maps;
import com.xcompany.nimble.biz.gameplay.data.mongo.hero.TeamType;
import com.xcompany.nimble.biz.gameplay.data.mongo.stage.StageType;
import com.xcompany.nimble.biz.gameplay.data.mongo.hero.BattlePosType;
import com.xcompany.nimble.biz.gameplay.data.mongo.hero.Hero;
import com.xcompany.nimble.biz.gameplay.data.numeric.ConstNumeric;
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
        this.teamMap.put(String.valueOf(TeamType.MAIN_PVE.getTypeCode()), Maps.newHashMap());

        for(int i = 0; i < ConstNumeric.START_TEAM.length; i++){
            String heroId = String.valueOf(ConstNumeric.START_TEAM[i]);
            if(!heroId.equals("0")) {
                this.getHeroMap().put(String.valueOf(heroId), Hero.builder().id(String.valueOf(heroId)).level(1).build());
                this.teamMap.get(String.valueOf(TeamType.MAIN_PVE.getTypeCode())).put(String.valueOf(i), "20001");
            }
        }

        this.getItemMap().put("1", Item.builder().id(1).count(ConstNumeric.START_COIN).build());
        this.getStageProgressMap().put(String.valueOf(StageType.MAIN_PVE_STAGE.getTypeCode()), ConstNumeric.START_STAGE);
    }
}
