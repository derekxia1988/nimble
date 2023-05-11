package com.xcompany.nimble.biz.gameplay.service.manager;

import com.google.common.collect.Maps;
import com.xcompany.nimble.base.net.ws.WSRespEvent;
import com.xcompany.nimble.biz.gameplay.data.mongo.Item;
import com.xcompany.nimble.biz.gameplay.data.mongo.Player;
import com.xcompany.nimble.biz.gameplay.data.protocol.Response.RespBase;
import com.xcompany.nimble.biz.gameplay.data.protocol.Response.RespItemData;
import com.xcompany.nimble.biz.gameplay.data.protocol.Response.RespOpCode;
import com.xcompany.nimble.biz.exception.BizErrorCode;
import com.xcompany.nimble.biz.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ItemManager {
    @Autowired
    private ApplicationEventPublisher publisher;
    public void cost(Player player, String itemId, int incr){
        int count = player.getItemMap().getOrDefault(itemId, Item.builder().build()).getCount();
        if(count < incr){
            throw new BizException(BizErrorCode.ITEM_NOT_ENOUGH, String.format("need item: {} need: {}, current:{}", itemId, incr, count));
        }

        this.incr(player, itemId, -incr);
    }

    public void incr(Player player, String itemId, int incr){
        if(!player.getItemMap().containsKey(itemId)){
            player.getItemMap().put(itemId, Item.builder().build());
        }

        Item item = player.getItemMap().get(itemId);
        item.incr(incr);

        RespItemData respItemData = RespItemData.builder().itemMap(Maps.newHashMap()).build();
        respItemData.getItemMap().put(itemId, item);
        RespBase respBase = RespBase.builder().pid(player.getId()).opCode(RespOpCode.ITEM_CHANGE.getOpCode()).respData(respItemData).build();
        this.publisher.publishEvent(new WSRespEvent(this, player.getId(), respBase));
    }
}
