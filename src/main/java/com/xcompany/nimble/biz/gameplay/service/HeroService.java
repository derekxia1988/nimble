package com.xcompany.nimble.biz.gameplay.service;

import com.xcompany.nimble.base.net.ws.WSRespEvent;
import com.xcompany.nimble.biz.exception.BizErrorCode;
import com.xcompany.nimble.biz.exception.BizException;
import com.xcompany.nimble.biz.gameplay.data.mongo.hero.Hero;
import com.xcompany.nimble.biz.gameplay.data.mongo.Player;
import com.xcompany.nimble.biz.gameplay.data.numeric.CharLevelNumeric;
import com.xcompany.nimble.biz.gameplay.data.protocol.Request.ReqHeroLvUpData;
import com.xcompany.nimble.biz.gameplay.data.protocol.Request.ReqLordLvUpData;
import com.xcompany.nimble.biz.gameplay.data.protocol.Response.RespBase;
import com.xcompany.nimble.biz.gameplay.data.protocol.Response.RespHeroData;
import com.xcompany.nimble.biz.gameplay.data.protocol.Response.RespLordData;
import com.xcompany.nimble.biz.gameplay.data.protocol.Response.RespOpCode;
import com.xcompany.nimble.biz.numeric.Numerics;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class HeroService extends BaseService {

    @EventListener(ReqHeroLvUpData.class)
    public void heroLevelUp(ReqHeroLvUpData reqHeroLvUpData) {
        Player player = reqHeroLvUpData.getPlayer();
        Hero hero = player.getHeroMap().get(reqHeroLvUpData.getHeroId());

        if(hero.getLevel() + reqHeroLvUpData.getLevel() > player.getLordLevel()){
            throw new BizException(BizErrorCode.HERO_LEVEL_EXCEED_LORD, String.format("lord level: {} reqeuest level: {}", player.getLordLevel(), hero.getLevel() + reqHeroLvUpData.getLevel()));
        }

        int needCoin = 0;
        for(int i = 0; i < reqHeroLvUpData.getLevel(); i++){
            log.info("Level: {}, i: {}", hero.getLevel(), i);
            needCoin += Numerics.get(CharLevelNumeric.class, hero.getLevel() + i).get().getHeroCost();
        }

        this.itemManager.cost(player, "1", needCoin);
        hero.setLevel(hero.getLevel() + reqHeroLvUpData.getLevel());

        RespHeroData respLevelUpData = new RespHeroData();
        respLevelUpData.getHeroMap().put(hero.getId(), hero);
        RespBase respBase = RespBase.builder().pid(player.getId()).opCode(RespOpCode.HERO_CHANGE.getOpCode()).respData(respLevelUpData).build();
        this.publisher.publishEvent(new WSRespEvent(this, player.getId(), respBase));
    }

    @EventListener(ReqLordLvUpData.class)
    public void lordLevelUp(ReqLordLvUpData reqHeroLvUpData) {
        Player player = reqHeroLvUpData.getPlayer();
        player.setLordLevel(player.getLordLevel() + reqHeroLvUpData.getLevel());
        int needCoin = 0;
        for(int i = 0; i < reqHeroLvUpData.getLevel(); i++){
            needCoin += Numerics.get(CharLevelNumeric.class, player.getLordLevel() + i).get().getLoardCost();
        }

        this.itemManager.cost(player, "1", needCoin);

        RespLordData respLordData = RespLordData.builder().lordLevel(player.getLordLevel()).build();
        RespBase respBase = RespBase.builder().pid(player.getId()).opCode(RespOpCode.LORD_LEVEL_UP.getOpCode()).respData(respLordData).build();
        this.publisher.publishEvent(new WSRespEvent(this, player.getId(), respBase));
    }
}
