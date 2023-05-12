package com.xcompany.nimble.base.management;

import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ShutdownProcessor {
//    @Autowired
//    protected ApplicationEventPublisher publisher;
//
//    @PreDestroy
//    public void shutdown(){
//        publisher.publishEvent(new ShutdownEvent(this));
//    }
}
