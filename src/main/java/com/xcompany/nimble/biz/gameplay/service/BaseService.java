package com.xcompany.nimble.biz.gameplay.service;

import com.xcompany.nimble.biz.gameplay.service.manager.ItemManager;
import com.xcompany.nimble.biz.gameplay.service.manager.RewardManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

public abstract class BaseService {
    @Autowired
    protected ApplicationEventPublisher publisher;

    @Autowired
    protected ItemManager itemManager;

    @Autowired
    protected RewardManager rewardManager;
}
