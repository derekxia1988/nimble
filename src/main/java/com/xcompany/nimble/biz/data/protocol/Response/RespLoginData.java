package com.xcompany.nimble.biz.data.protocol.Response;

import com.xcompany.nimble.biz.data.mongo.Player;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RespLoginData implements RespData {
    private final Player player;
}
