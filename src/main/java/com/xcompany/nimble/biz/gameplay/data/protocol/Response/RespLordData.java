package com.xcompany.nimble.biz.gameplay.data.protocol.Response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RespLordData implements RespData{
    private int lordLevel = 0;
}
