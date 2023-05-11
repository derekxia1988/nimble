package com.xcompany.nimble.biz.gameplay.data.protocol.Request;

import com.xcompany.nimble.biz.gameplay.data.protocol.Request.ReqData;
import lombok.Data;

@Data
public class ReqLordLvUpData extends ReqData {
    private int level;

    public int getLevel() {
        return level;
    }
}
