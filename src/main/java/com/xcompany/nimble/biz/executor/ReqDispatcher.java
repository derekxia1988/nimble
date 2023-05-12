package com.xcompany.nimble.biz.executor;

import com.alibaba.fastjson.JSON;
import com.xcompany.nimble.base.net.ws.WSReqEvent;
import com.xcompany.nimble.base.net.ws.WSRespEvent;
import com.xcompany.nimble.biz.exception.BizErrorCode;
import com.xcompany.nimble.biz.exception.BizException;
import com.xcompany.nimble.biz.gameplay.data.mongo.Player;
import com.xcompany.nimble.biz.gameplay.data.protocol.Request.ReqData;
import com.xcompany.nimble.biz.gameplay.data.protocol.Request.ReqOpCode;
import com.xcompany.nimble.biz.db.PlayerRepository;
import com.xcompany.nimble.biz.gameplay.data.protocol.Response.RespBase;
import com.xcompany.nimble.biz.gameplay.data.protocol.Response.RespErrorData;
import com.xcompany.nimble.biz.gameplay.data.protocol.Response.RespOpCode;
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
        String pid = event.getJsonObject().getString("pid");
        int opCode = event.getJsonObject().getIntValue("opCode");
        Player player = this.playerRepository.findById(pid);

        try {
            if (player == null) {
                player = new Player(pid);
                player.init();
                this.playerRepository.insert(player);
            }

            ReqData reqData = JSON.toJavaObject(event.getJsonObject().getJSONObject("reqData"), ReqOpCode.getClzByOpCode(opCode));
            reqData.setPlayer(player);
            this.publisher.publishEvent(reqData);
        } catch (BizException bizException){
            RespErrorData respErrorData = RespErrorData.builder().reqOpCode(opCode).errorCode(bizException.getBizErrorCode().getCode()).msg(bizException.getMessage()).build();
            RespBase respBase = RespBase.builder().pid(pid).opCode(RespOpCode.ERROR.getOpCode()).respData(respErrorData).build();
            this.publisher.publishEvent(new WSRespEvent(this, pid, respBase));

            log.error("[BizError] OpCode: {}, ErrorCode: {}, Msg: {} Exception: {}", opCode, bizException.getBizErrorCode().getCode(), bizException.getMessage(), bizException.toString());
        } catch (Exception exception){
            RespErrorData respErrorData = RespErrorData.builder().reqOpCode(opCode).errorCode(BizErrorCode.UNKNOWN_ERROR.getCode()).msg("Unknown server error.").build();
            RespBase respBase = RespBase.builder().pid(pid).opCode(RespOpCode.ERROR.getOpCode()).respData(respErrorData).build();
            this.publisher.publishEvent(new WSRespEvent(this, pid, respBase));

            log.error("[Unknown Error] OpCode: {}, Exception: {}", opCode, exception.toString());
        }


        this.playerRepository.save(player);
    }
}
