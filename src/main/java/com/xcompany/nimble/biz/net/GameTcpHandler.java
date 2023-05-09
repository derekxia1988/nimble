package com.xcompany.nimble.biz.net;

import com.xcompany.nimble.base.executor.OrderedExecutor;
import com.xcompany.nimble.base.net.tcp.TcpHandler;
import gamebase.net.base.channel.NetChannel;
import gamebase.net.base.msg.NetMsg;
import gamebase.net.base.msg.NetMsgEnum;
import io.netty.handler.codec.DecoderException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Log4j2
@Component
public class GameTcpHandler extends TcpHandler {
    private static final String[] EXCLUDE_MSG = {
            "Connection reset by peer",
            "远程主机强迫关闭了一个现有的连接",
            "断开的管道"
    };

    private final OrderedExecutor<String> bizExecutor;

    public GameTcpHandler(OrderedExecutor<String> bizExecutor) {
        this.bizExecutor = bizExecutor;
    }

    @Override
    public void messageReceived(NetChannel channel, NetMsg netMsg) {
        if (netMsg.getType() != NetMsgEnum.DATA) {
            return;
        }

        try {
            // 从这里进入业务逻辑处理
//            bizExecutor.execute(rid, bizTask);
        } catch (Throwable t) {
            log.error("resolve request error.", t);
        }

    }

    @Override
    public void exceptionCaught(NetChannel channel, Throwable cause) {
        if (cause instanceof IOException) {
            String exStr = cause.getMessage();
            if (Arrays.stream(EXCLUDE_MSG).noneMatch(exStr::contains)) {
                log.error("exception when channel {}.", channel, cause);
                return;
            }
        }

        if (cause instanceof DecoderException) {
            log.debug("Exception caught, channel:{},exception:{}", channel,
                    cause.toString());
            return;
        }
        log.error("Exception caught, channel:" + channel, cause);
    }
}
