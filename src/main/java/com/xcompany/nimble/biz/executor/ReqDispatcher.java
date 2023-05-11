package com.xcompany.nimble.biz.executor;

import com.alibaba.fastjson.JSON;
import com.xcompany.nimble.base.net.ws.WSReqEvent;
import com.xcompany.nimble.biz.gameplay.data.mongo.Player;
import com.xcompany.nimble.biz.gameplay.data.protocol.Request.ReqData;
import com.xcompany.nimble.biz.gameplay.data.protocol.Request.ReqOpCode;
import com.xcompany.nimble.biz.db.PlayerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ReqDispatcher implements ApplicationListener<WSReqEvent> {
    @Autowired
    protected ApplicationEventPublisher publisher;
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
            player = new Player(pid);
            player.init();
            this.playerRepository.insert(player);
        }

        ReqData reqData = JSON.toJavaObject(event.getJsonObject().getJSONObject("reqData"), ReqOpCode.getClzByOpCode(opCode));
        reqData.setPlayer(player);
        this.publisher.publishEvent(reqData);


        this.playerRepository.save(player);
    }
}
