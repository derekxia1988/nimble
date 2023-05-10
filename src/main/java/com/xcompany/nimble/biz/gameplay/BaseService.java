package com.xcompany.nimble.biz.gameplay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

public abstract class BaseService {
    @Autowired
    protected ApplicationEventPublisher publisher;
}
