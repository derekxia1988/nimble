/*
 * Copyright (c) 2021.  Topjoy all rights reserved.
 */

package com.xcompany.nimble.base.net.tcp;

import gamebase.net.tcp.netty.NettyTcpConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiadong
 * @since 2021/09/14
 **/
@Configuration
@ConfigurationProperties("net.tcp")
public class TcpConfiguration {
    private int port;
    private NettyTcpConfig nettyConfig = new NettyTcpConfig();

    @Bean
    @ConditionalOnBean(TcpHandler.class)
    @ConditionalOnMissingBean
    TcpServer tcpServer(TcpHandler tcpHandler) {
        return new TcpServer(tcpHandler, nettyConfig, port);
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public NettyTcpConfig getNettyConfig() {
        return nettyConfig;
    }

    public void setNettyConfig(NettyTcpConfig nettyConfig) {
        this.nettyConfig = nettyConfig;
    }
}