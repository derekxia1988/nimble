package com.xcompany.nimble.biz.gameplay.service;

import com.alibaba.fastjson.JSON;
import com.xcompany.nimble.base.net.ws.WSRespEvent;
import com.xcompany.nimble.biz.gameplay.data.mongo.Player;
import com.xcompany.nimble.biz.gameplay.data.protocol.Request.ReqLoginData;
import com.xcompany.nimble.biz.gameplay.data.protocol.Response.RespBase;
import com.xcompany.nimble.biz.gameplay.data.protocol.Response.RespLoginData;
import com.xcompany.nimble.biz.gameplay.data.protocol.Response.RespOpCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PlayerService extends BaseService {
    @EventListener(ReqLoginData.class)
    public void login(ReqLoginData loginData){
        log.info("FUCK");
        Player player = loginData.getPlayer();

        RespLoginData respLoginData = RespLoginData.builder().player(player).build();
        RespBase respBase = RespBase.builder().pid(player.getId()).opCode(RespOpCode.LOGIN.getOpCode()).respData(respLoginData).build();
        log.error("返回对象:{}", JSON.toJSONString(respLoginData));
        publisher.publishEvent(new WSRespEvent(player, player.getId(), respBase));
    }
}
