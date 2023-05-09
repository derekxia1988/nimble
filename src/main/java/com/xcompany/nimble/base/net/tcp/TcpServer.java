package com.xcompany.nimble.base.net.tcp;


import com.xcompany.nimble.base.exception.StartServerException;
import gamebase.net.base.channel.ChannelService;
import gamebase.net.base.push.PushService;
import gamebase.net.tcp.netty.NettyTcpConfig;
import gamebase.net.tcp.netty.NettyTcpNet;
import gamebase.net.tcp.netty.standard.NettyTcpInitializer;
import gamebase.net.tcp.netty.standard.NettyTcpNetStandard;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;

/**
 * @author xiadong
 * @since 2021/09/14
 **/
@Log4j2
public class TcpServer {
    private final TcpHandler tcpHandler;
    private final NettyTcpConfig nettyConfig;
    private final int port;
    private NettyTcpNet net;

    public TcpServer(TcpHandler tcpHandler, NettyTcpConfig nettyConfig, int port) {
        this.tcpHandler = tcpHandler;
        this.nettyConfig = nettyConfig;
        this.port = port;
    }

    @PostConstruct
    public void start() {
        if (port <= 0) {
            throw new StartServerException("try start tcp server but port %s is illegal".formatted(port));
        }
        net = new NettyTcpNetStandard();
        net.init(nettyConfig);
        if (!net.start(port, new NettyTcpInitializer(tcpHandler))) {
            throw new StartServerException("start tcp server failure, port: %s".formatted(port));
        }
        log.info("tcp server started, port: {}", port);
    }

    @PreDestroy
    public void stop() {
        if (net != null) {
            net.stop();
        }
        PushService.shutdown();
        ChannelService.shutdown();
    }
}
