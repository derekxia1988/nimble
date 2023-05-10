package com.xcompany.nimble.base.net.ws;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ws消息处理类
 */
@Component
@Log4j2
public class WebSocketHandler extends AbstractWebSocketHandler implements ApplicationListener<WSRespEvent> {
    private final ApplicationEventPublisher publisher;

    private final ConcurrentHashMap<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    public WebSocketHandler(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("建立ws连接: {}", session);

        // cache session
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 获得客户端传来的消息
        String payload = message.getPayload();
        log.info("server 接收到消息 " + payload);
        JSONObject req = JSONObject.parseObject(payload);
        String pid = req.getString("pid");

        WebSocketSession currentSession = this.sessionMap.getOrDefault(pid, null);
        if(currentSession != session){
            if(currentSession != null) {
                this.sessionMap.get(pid).close();
            }

            sessionMap.putIfAbsent(pid, session);
        }

        publisher.publishEvent(new WSReqEvent(session, req));
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        log.info("发送二进制消息");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("异常处理");
        // handle exception
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("关闭ws连接");
        // remove session
    }

    @Override
    public void onApplicationEvent(WSRespEvent event) {
        log.error("接受到推送：{}", JSON.toJSONString(event.getMsg()));
        try {
            this.sessionMap.get(event.getPid()).sendMessage(new TextMessage(JSON.toJSONString(event.getMsg())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
