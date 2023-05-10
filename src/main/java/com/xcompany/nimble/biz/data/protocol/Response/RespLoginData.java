package com.xcompany.nimble.biz.data.protocol.Response;

import com.xcompany.nimble.biz.data.mongo.Player;
import lombok.Builder;

@Builder
public class RespLoginData implements RespData {
    private final Player player;

    public Player getPlayer() {
        return player;
    }
}
