package com.xcompany.nimble.biz.gameplay.data.protocol.Request;

import com.xcompany.nimble.biz.gameplay.data.mongo.Player;

public abstract class ReqData {
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
