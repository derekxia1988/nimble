package com.xcompany.nimble.biz.executor;

import com.xcompany.nimble.base.net.ws.WSReqEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class BizDispatcher implements ApplicationListener<WSReqEvent> {
    @Override
    public void onApplicationEvent(WSReqEvent event) {
        log.info("pid:{}", event.getJsonObject().getIntValue("pid"));
    }
}
