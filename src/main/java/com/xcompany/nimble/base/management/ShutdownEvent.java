package com.xcompany.nimble.base.management;

import org.springframework.context.ApplicationEvent;

public class ShutdownEvent extends ApplicationEvent {
    public ShutdownEvent(Object source) {
        super(source);
    }
}
