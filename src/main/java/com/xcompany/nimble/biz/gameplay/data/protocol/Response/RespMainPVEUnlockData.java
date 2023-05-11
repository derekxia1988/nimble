package com.xcompany.nimble.biz.gameplay.data.protocol.Response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RespMainPVEUnlockData implements RespData {
    private final int nextStageId;
    private final int nextLevelId;
}
