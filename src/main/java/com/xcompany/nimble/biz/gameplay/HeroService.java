package com.xcompany.nimble.biz.gameplay;

import com.xcompany.nimble.base.net.ws.WSRespEvent;
import com.xcompany.nimble.biz.data.mongo.Hero;
import com.xcompany.nimble.biz.data.mongo.Item;
import com.xcompany.nimble.biz.data.mongo.Player;
import com.xcompany.nimble.biz.data.protocol.Request.ReqHeroLvUpData;
import com.xcompany.nimble.biz.data.protocol.Response.RespBase;
import com.xcompany.nimble.biz.data.protocol.Response.RespLevelUpData;
import com.xcompany.nimble.biz.data.protocol.Response.RespOpCode;
import org.springframework.stereotype.Service;

@Service
public class HeroService extends BaseService{

    public void heroLevelUp(Player player, ReqHeroLvUpData reqHeroLvUpData) {
        Hero hero = player.getHeroMap().get(reqHeroLvUpData.getHeroId());
        hero.setLevel(hero.getLevel() + reqHeroLvUpData.getLevel());
        Item coin = player.getItemMap().get("1");
        coin.setCount(coin.getCount() - reqHeroLvUpData.getLevel() * 100);

        RespLevelUpData respLevelUpData = new RespLevelUpData();
        respLevelUpData.getItemMap().put("1", coin);
        respLevelUpData.getHeroMap().put(hero.getId(), hero);
        RespBase respBase = RespBase.builder().pid(player.getId()).opCode(RespOpCode.HERO_LEVEL_UP.getOpCode()).respData(respLevelUpData).build();
        this.publisher.publishEvent(new WSRespEvent(this, player.getId(), respBase));
    }

    public void lordLevelUp(Player player, ReqHeroLvUpData reqHeroLvUpData) {
        player.setLordLevel(player.getLordLevel() + reqHeroLvUpData.getLevel());
        Item coin = player.getItemMap().get("1");
        coin.setCount(coin.getCount() - reqHeroLvUpData.getLevel() * 100);

        RespLevelUpData respLevelUpData = new RespLevelUpData();
        respLevelUpData.getItemMap().put("1", coin);
        respLevelUpData.setLordLevel(player.getLordLevel());
        RespBase respBase = RespBase.builder().pid(player.getId()).opCode(RespOpCode.LORD_LEVEL_UP.getOpCode()).respData(respLevelUpData).build();
        this.publisher.publishEvent(new WSRespEvent(this, player.getId(), respBase));
    }
}
