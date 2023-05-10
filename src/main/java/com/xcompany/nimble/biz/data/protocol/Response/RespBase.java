package com.xcompany.nimble.biz.data.protocol.Response;


import com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RespBase {
    private final String pid;
    private final int opCode;
    private final RespData respData;
}
