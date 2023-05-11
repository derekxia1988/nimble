package com.xcompany.nimble.biz.gameplay.data.protocol.Response;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RespBase {
    private final String pid;
    private final int opCode;
    private final RespData respData;
}
