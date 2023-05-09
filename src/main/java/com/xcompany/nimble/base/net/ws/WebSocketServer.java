package com.xcompany.nimble.base.net.ws;

/*
 * @ ServerEndpoint
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Log4j2
@Service
@ServerEndpoint("/api/webSocket/{sid}")
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //接收sid
    private String sid = "";

    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid){
        this.session = session;
        webSocketSet.add(this);
        this.sid = sid;
        addOnlineCount();
        try {
            sendMessage("conn_success");
            log.info("有新窗口开始监听:" + sid + ",当前在线人数为:" + getOnlineCount());
        } catch (IOException e) {
            log.error("websocket IO Exception");
        }
    }

    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        log.debug("remove this: {}", this);
        subOnlineCount();
        //断开连接，更新占用情况
        log.info("释放的sid为："+sid);
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @ Param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session){
        log.info("收到来自窗口" + sid + "的信息:" + message);
        //群发消息
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error){
        log.error("{}发生错误",session);
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
    // 没啥用，重复的
    public static void sendInfo(String message, @PathParam("sid") String sid){
        log.info("推送消息到窗口" + sid + "，推送内容:" + message);
        for(WebSocketServer item : webSocketSet){
            try{
                if(message.isEmpty()){
                    item.sendMessage(message);
                } else if (item.sid.equals(sid)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    public static CopyOnWriteArraySet getWebSocketSet() {
        return webSocketSet;
    }
}