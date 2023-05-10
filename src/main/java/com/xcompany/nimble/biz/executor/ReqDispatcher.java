package com.xcompany.nimble.biz.executor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.xcompany.nimble.base.net.ws.WSReqEvent;
import com.xcompany.nimble.biz.data.mongo.Player;
import com.xcompany.nimble.biz.data.protocol.Request.ReqHeroLvUpData;
import com.xcompany.nimble.biz.data.protocol.Request.ReqOpCode;
import com.xcompany.nimble.biz.db.PlayerRepository;
import com.xcompany.nimble.biz.gameplay.HeroService;
import com.xcompany.nimble.biz.gameplay.PlayerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Log4j2
public class ReqDispatcher implements ApplicationListener<WSReqEvent> {
    @Autowired
    private PlayerService playerService;

    @Autowired
    private HeroService heroService;

    private final PlayerRepository playerRepository;
    public ReqDispatcher(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void onApplicationEvent(WSReqEvent event) {
        log.error((event.getJsonObject().toJSONString()));
        String pid = event.getJsonObject().getString("pid");
        int opCode = event.getJsonObject().getIntValue("opCode");
        Player player = this.playerRepository.findById(pid);

        if(player == null) {
            player = Player.builder().id(pid).build();
            player.init();
            this.playerRepository.insert(player);
        }

        if(opCode == ReqOpCode.LOGIN.getOpCode()){
            this.playerService.login(player, false);
        }
        else if(opCode == ReqOpCode.HERO_LEVEL_UP.getOpCode()){
            this.heroService.heroLevelUp(player, JSON.toJavaObject(event.getJsonObject().getJSONObject("reqData"), ReqHeroLvUpData.class));
        }
        else if(opCode == ReqOpCode.LORD_LEVEL_UP.getOpCode()){
            this.heroService.lordLevelUp(player, JSON.toJavaObject(event.getJsonObject().getJSONObject("reqData"), ReqHeroLvUpData.class));
        }

        this.playerRepository.save(player);
    }
}
