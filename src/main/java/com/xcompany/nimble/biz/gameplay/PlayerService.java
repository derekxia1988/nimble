package com.xcompany.nimble.biz.gameplay;

import com.alibaba.fastjson.JSON;
import com.xcompany.nimble.base.net.ws.WSRespEvent;
import com.xcompany.nimble.biz.data.mongo.Player;
import com.xcompany.nimble.biz.data.protocol.Response.RespBase;
import com.xcompany.nimble.biz.data.protocol.Response.RespLoginData;
import com.xcompany.nimble.biz.data.protocol.Response.RespOpCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PlayerService extends BaseService {
    public void login(Player player, boolean isNew){
        RespLoginData respLoginData = RespLoginData.builder().player(player).build();
        RespBase respBase = RespBase.builder().pid(player.getId()).opCode(RespOpCode.LOGIN.getOpCode()).respData(respLoginData).build();
//        log.error("返回对象:{}", JSON.toJSONString(respLoginData));
        publisher.publishEvent(new WSRespEvent(player, player.getId(), respBase));
    }
}
