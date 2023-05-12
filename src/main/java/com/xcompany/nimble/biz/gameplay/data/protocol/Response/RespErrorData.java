package com.xcompany.nimble.biz.gameplay.data.protocol.Response;

import lombok.Builder;

@Builder
public class RespErrorData implements RespData {
    private final int reqOpCode;
    private final int errorCode;
    private final String msg;
}
